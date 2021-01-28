package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.EvaluationsDao;
import com.lawencon.elearning.model.Evaluations;
import com.lawencon.elearning.model.Grades;
import com.lawencon.elearning.model.SubmissionStatus;
import com.lawencon.elearning.model.SubmissionStatusRenewal;

/**
 * @author Nur Alfilail
 */

@Service
public class EvaluationsServiceImpl extends BaseServiceImpl implements EvaluationsService {

	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	private EvaluationsDao evaluationsDao;

	@Autowired
	private GradesService gradesService;

	@Autowired
	private SubmissionStatusRenewalsService statusRenewalService;

	@Autowired
	private SubmissionStatusService statusService;

	@Override
	public void insertEvaluation(Evaluations evaluation) throws Exception {
		Grades grade = gradesService.getGradeByScore(evaluation.getScore());
		evaluation.setIdGrade(grade);
		evaluationsDao.insertEvaluation(evaluation, () -> validateInsert(evaluation));
		insertStatusRenewal(evaluation);
		System.out.println("Sending Email...");
		sendEmail(evaluation);
		System.out.println("Done");
	}

	@Override
	public List<Evaluations> getAllEvaluations() throws Exception {
		return evaluationsDao.getAllEvaluations();
	}

	@Override
	public Evaluations getEvaluationById(String id) throws Exception {
		return evaluationsDao.getEvaluationById(id);
	}

	@Override
	public Evaluations getEvaluationByCode(String code) throws Exception {
		return evaluationsDao.getEvaluationByCode(code);
	}

	private void insertStatusRenewal(Evaluations evaluation) throws Exception {
		SubmissionStatus submissionStatus = statusService.getSubmissionStatusByCode("SNT");
		SubmissionStatusRenewal statusRenewal = new SubmissionStatusRenewal();
		statusRenewal.setIdAssignmentSubmission(evaluation.getIdAssignmentSubmission());
		statusRenewal.setIdSubmissionStatus(submissionStatus);
		statusRenewalService.insertSubmissionStatusRenewal(statusRenewal);
	}

	private void validateInsert(Evaluations evaluation) throws Exception {

	}

	private void sendEmail(Evaluations evaluation) throws Exception {
		String participantEmail = evaluationsDao.getParticipantEmail(evaluation);
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(participantEmail);
		msg.setSubject("Score of Assignment Submission Has Updated");
		msg.setText("Dear You, \n Score of your assignment submissions has updated. "
				+ "Have a nice day. \n \n Best Regards, \n Elearning Alfione");
		javaMailSender.send(msg);
	}

	@Override
	public List<?> reportAllScore(String idClass) throws Exception {
		return evaluationsDao.reportAllScore(idClass);
	}

	@Override
	public List<?> reportScore(String idDtlClass, String idParticipant) throws Exception {
		return evaluationsDao.reportScore(idDtlClass, idParticipant);
	}

}
