package com.lawencon.elearning.service;

import java.time.LocalDate;
import java.time.LocalTime;
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
import com.lawencon.elearning.model.SubmissionStatusRenewal;
import com.lawencon.elearning.util.GeneralUtil;
import com.lawencon.elearning.util.MailUtil;
import com.lawencon.elearning.util.SubmissionStatusCode;
import com.lawencon.elearning.util.TransactionNumberCode;

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
		try {
			begin();
			Files file = new Files();
			file.setFile(fileInput.getBytes());
			file.setType(fileInput.getContentType());
			filesService.insert(file);
			assignmentSubmission.setIdFile(file);
			assignmentSubmission.setSubmitTime(LocalTime.now());
			assignmentSubmission.setTrxNumber(generateTrxNumber(TransactionNumberCode.ASSIGNMENT_SUBMISSION.code));
			assignmentSubmissionsDao.insertAssignmentSubmission(assignmentSubmission,
					() -> validateInsert(assignmentSubmission));
			insertStatusRenewal(assignmentSubmission);
			System.out.println("Sending Email...");
			sendEmailTutor(assignmentSubmission);
			sendEmailParticipant(assignmentSubmission);
			System.out.println("Done");
			commit();
		} catch (Exception e) {
			rollback();
			throw new Exception(e);
		}
	}

	@Override
	public void updateAssignmentSubmission(AssignmentSubmissions assignmentSubmission, MultipartFile fileInput)
			throws Exception {
		try {
			begin();
			Files file = filesService.getById(assignmentSubmission.getIdFile().getId());
			file.setFile(fileInput.getBytes());
			file.setType(fileInput.getContentType());
			filesService.update(file);
			commit();
		} catch (Exception e) {
			rollback();
			throw new Exception();
		}
	}

	@Override
	public List<AssignmentSubmissions> getAllAssignmentSubmissions() throws Exception {
		return assignmentSubmissionsDao.getAllAssignmentSubmissions();
	}

	@Override
	public AssignmentSubmissions getByIdDtlModuleRgsAndIdParticipant(String idDtlModuleRgs, String idParticipant)
			throws Exception {
		return assignmentSubmissionsDao.getByIdDtlModuleRgsAndIdParticipant(idDtlModuleRgs, idParticipant);
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

		General general = generalService.getTemplateEmail(GeneralUtil.ASSIGNMENT_SUBMISSION_TUTOR.code);
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

		General general = generalService.getTemplateEmail(GeneralUtil.ASSIGNMENT_SUBMISSION_PARTICIPANT.code);
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
		SubmissionStatusRenewal statusRenewal = new SubmissionStatusRenewal();
		statusRenewal.setIdAssignmentSubmission(assignmentSubmission);
		statusRenewal
				.setIdSubmissionStatus(statusService.getByCode(SubmissionStatusCode.UPLOADED.code));
		statusRenewalService.insertSubmissionStatusRenewal(statusRenewal);
	}

	private void validateInsert(AssignmentSubmissions assignmentSubmissions) {

	}

}
