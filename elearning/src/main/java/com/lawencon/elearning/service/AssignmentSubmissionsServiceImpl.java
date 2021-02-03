package com.lawencon.elearning.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.elearning.dao.AssignmentSubmissionsDao;
import com.lawencon.elearning.helper.MailHelper;
import com.lawencon.elearning.model.AssignmentSubmissions;
import com.lawencon.elearning.model.Files;
import com.lawencon.elearning.model.General;
import com.lawencon.elearning.model.Profiles;
import com.lawencon.elearning.model.SubmissionStatus;
import com.lawencon.elearning.model.SubmissionStatusRenewal;
import com.lawencon.elearning.util.MailUtil;

@Service
public class AssignmentSubmissionsServiceImpl extends ElearningBaseServiceImpl implements AssignmentSubmissionsService {

	@Autowired
	private AssignmentSubmissionsDao assignmentSubmissionsDao;

	@Autowired
	private SubmissionStatusRenewalsService statusRenewalService;

	@Autowired
	private SubmissionStatusService statusService;

	@Autowired
	private FilesService filesService;
	
	@Autowired
	private GeneralService generalService;
	
	@Autowired
	private MailUtil mailUtil;

	@Override
	public void insertAssignmentSubmissions(AssignmentSubmissions assignmentSubmission, MultipartFile fileInput)
			throws Exception {
		Files file = new Files();
		file.setFile(fileInput.getBytes());
		file.setType(fileInput.getContentType());
		filesService.insertFile(file);
		assignmentSubmission.setIdFile(file);
		assignmentSubmission.setSubmitDateTime(LocalDateTime.now());
		assignmentSubmission.setTrxNumber(generateTrxNumber());
		assignmentSubmissionsDao.insertAssignmentSubmission(assignmentSubmission,
				() -> validateInsert(assignmentSubmission));
		insertStatusRenewal(assignmentSubmission);
		System.out.println("Sending Email...");
		sendEmailTutor(assignmentSubmission);
		sendEmailParticipant(assignmentSubmission);
		System.out.println("Done");
	}

	@Override
	public List<AssignmentSubmissions> getAllAssignmentSubmissions() throws Exception {
		return assignmentSubmissionsDao.getAllAssignmentSubmissions();
	}

	@Override
	public AssignmentSubmissions getAssignmentSubmissionsByCode(String code) throws Exception {
		return assignmentSubmissionsDao.getAssignmentSubmissionByCode(code);
	}

	@Override
	public AssignmentSubmissions getAssignmentSubmissionsById(String id) throws Exception {
		return assignmentSubmissionsDao.getAssignmentSubmissionById(id);
	}

	@Override
	public void deleteAssignmentSubmissionsById(String id) throws Exception {
		assignmentSubmissionsDao.deleteAssignmentSubmissionById(id);
	}

	private void sendEmailTutor(AssignmentSubmissions assignmentSubmission) throws Exception {
		Profiles tutor = assignmentSubmissionsDao.getTutorProfile(assignmentSubmission);
		Profiles participant = assignmentSubmissionsDao.getParticipantProfile(assignmentSubmission);
		
		General general = generalService.getTemplateEmail("asgttr");
		String text = general.getTemplateHtml();
		
		text = text.replace("#1#", tutor.getFullName());
		text = text.replace("#2#", participant.getFullName());
		
		MailHelper mailHelper = new MailHelper();
		mailHelper.setFrom("elearningalfione@gmail.com");
		mailHelper.setTo(tutor.getEmail());
		mailHelper.setSubject("Assignment Submission Has Sent");
		mailHelper.setText(text);
		new MailServiceImpl(mailUtil, mailHelper).start();
	}

	private void sendEmailParticipant(AssignmentSubmissions assignmentSubmission) throws Exception {
		Profiles participant = assignmentSubmissionsDao.getParticipantProfile(assignmentSubmission);
		
		General general = generalService.getTemplateEmail("asgpcp");
		String text = general.getTemplateHtml();
		
		text = text.replace("#1#", participant.getFullName());
		
		MailHelper mailHelper = new MailHelper();
		mailHelper.setFrom("elearningalfione@gmail.com");
		mailHelper.setTo(participant.getEmail());
		mailHelper.setSubject("Assignment Submission Has Sent");
		mailHelper.setText(text);
		new MailServiceImpl(mailUtil, mailHelper).start();
	}

	private void insertStatusRenewal(AssignmentSubmissions assignmentSubmission) throws Exception {
		SubmissionStatus submissionStatus = statusService.getSubmissionStatusByCode("SNT");
		SubmissionStatusRenewal statusRenewal = new SubmissionStatusRenewal();
		statusRenewal.setIdAssignmentSubmission(assignmentSubmission);
		statusRenewal.setIdSubmissionStatus(submissionStatus);
		statusRenewalService.insertSubmissionStatusRenewal(statusRenewal);
	}

	private void validateInsert(AssignmentSubmissions assignmentSubmissions) {

	}

	private String generateTrxNumber() {
		Random random = new Random();
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yy-MM-dd");
		String formattedDate = localDate.format(myFormat);
		String trxCodeValue = String.valueOf(random.nextInt((999 + 1 - 100) + 100));
		String trx = bBuilder(formattedDate).toString();
		trx = trx.replaceAll("-", "");
		String trxNumber = bBuilder("ASB-", trx, "-", trxCodeValue).toString();
		return trxNumber;
	}

}
