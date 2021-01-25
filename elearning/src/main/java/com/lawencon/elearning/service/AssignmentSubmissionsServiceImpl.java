package com.lawencon.elearning.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.AssignmentSubmissionsDao;
import com.lawencon.elearning.model.AssignmentSubmissions;

@Service
public class AssignmentSubmissionsServiceImpl extends BaseServiceImpl implements AssignmentSubmissionsService{	
	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	private AssignmentSubmissionsDao assignmentSubmissionsDao;
	
	
	@Override
	public void insertAssignmentSubmissions(AssignmentSubmissions assignmentSubmission, MultipartFile file)
			throws Exception {
		assignmentSubmission.setFile(file.getBytes());
		assignmentSubmission.setFileType(file.getContentType());
		assignmentSubmission.setSubmitDateTime(LocalDateTime.now());
		assignmentSubmissionsDao.insertAssignmentSubmission(assignmentSubmission, 
				()->validateInsert(assignmentSubmission));
		System.out.println("Sending Email...");
		sendEmail();
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
	public void updateScore(AssignmentSubmissions assignmentSubmissions)
			throws Exception {
		AssignmentSubmissions assignmentSubmission = 
				assignmentSubmissionsDao.getAssignmentSubmissionById(assignmentSubmissions.getId());
		assignmentSubmission.setScore(assignmentSubmissions.getScore());
		assignmentSubmission.setIdGrade(assignmentSubmissions.getIdGrade());
		assignmentSubmissionsDao.updateAssignmentSubmission(assignmentSubmission, ()->validateUpdate(assignmentSubmission));
		System.out.println("Sending Email...");
		sendEmailUpdateScore();
		System.out.println("Done");
	}
	@Override
	public void deleteAssignmentSubmissionsById(String id) throws Exception {
		assignmentSubmissionsDao.deleteAssignmentSubmissionById(id);
	}
	
	void sendEmail() {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo("annisaanfz@gmail.com");

		msg.setSubject("Assignment Submission Has Sent");
		msg.setText("Dear You, \n Your Assignment Submissions has sent. "
				+ "Wait for updated score of your assignment submissions. "
				+ "Have a nice day. \n \n Best Regards, \n Elearning Alfione");

		javaMailSender.send(msg);

	}
	
	void sendEmailUpdateScore() {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo("nur.alfilail96@gmail.com");

		msg.setSubject("Score of Assignment Submission Has Updated");
		msg.setText("Dear You, \n Score of your assignment submissions has updated. "
				+ "Have a nice day. \n \n Best Regards, \n Elearning Alfione");

		javaMailSender.send(msg);

	}
	
	private void validateInsert(AssignmentSubmissions assignmentSubmissions) {

	}

	private void validateUpdate(AssignmentSubmissions assignmentSubmissions) {

	}

}
