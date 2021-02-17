package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.constant.SubmissionStatusCode;
import com.lawencon.elearning.constant.TemplateEmail;
import com.lawencon.elearning.constant.TransactionNumberCode;
import com.lawencon.elearning.dao.EvaluationsDao;
import com.lawencon.elearning.helper.MailHelper;
import com.lawencon.elearning.helper.ScoreInputs;
import com.lawencon.elearning.model.AssignmentSubmissions;
import com.lawencon.elearning.model.Evaluations;
import com.lawencon.elearning.model.General;
import com.lawencon.elearning.model.Grades;
import com.lawencon.elearning.model.Profiles;
import com.lawencon.elearning.model.SubmissionStatusRenewal;
import com.lawencon.elearning.util.MailUtil;

/**
 * @author Nur Alfilail
 */

@Service
public class EvaluationsServiceImpl extends ElearningBaseServiceImpl implements EvaluationsService {

	@Autowired
	private EvaluationsDao evaluationsDao;

	@Autowired
	private GradesService gradesService;

	@Autowired
	private SubmissionStatusRenewalsService statusRenewalService;

	@Autowired
	private SubmissionStatusService statusService;

	@Autowired
	private AssignmentSubmissionsService assignmentSubmissionsService;

	@Autowired
	private GeneralService generalService;

	@Autowired
	private MailUtil mailUtil;

	@Override
	public void insertEvaluation(ScoreInputs scores) throws Exception {
		try {
			begin();
			for (Evaluations evaluation : scores.getEvaluations()) {
				Grades grade = gradesService.getByScore(evaluation.getScore());
				evaluation.setIdGrade(grade);
				evaluation.setTrxNumber(generateTrxNumber(TransactionNumberCode.EVALUATION.code));
				evaluationsDao.insertEvaluation(evaluation, () -> validateInsert(evaluation));
				System.out.println("Sending Email...");
				sendEmail(evaluation);
				System.out.println("Done");
				insertStatusRenewal(evaluation);
			}
			commit();
		} catch (Exception e) {
			rollback();
			throw new Exception(e);
		}
	}

	@Override
	public void updateEvaluation(ScoreInputs scores) throws Exception {
		for (Evaluations evaluation : scores.getEvaluations()) {
			Grades grade = gradesService.getByScore(evaluation.getScore());
			evaluation.setIdGrade(grade);
			Evaluations eval = evaluationsDao.getEvaluationById(evaluation.getId());
			evaluation.setCreatedAt(eval.getCreatedAt());
			evaluation.setCreatedBy(eval.getCreatedBy());
			evaluation.setUpdatedBy(eval.getCreatedBy());
			evaluation.setTrxDate(eval.getTrxDate());
			evaluation.setTrxNumber(eval.getTrxNumber());
			evaluationsDao.updateEvaluation(evaluation, () -> validateUpdate(evaluation));
		}
	}

	@Override
	public List<Evaluations> getAllEvaluations() throws Exception {
		return evaluationsDao.getAllEvaluations();
	}

	@Override
	public List<Evaluations> getAllByIdDtlClassAndIdDtlModuleRgs(String idDtlClass, String idDtlModuleRgs)
			throws Exception {
		return evaluationsDao.getAllByIdDtlClassAndIdDtlModuleRgs(idDtlClass, idDtlModuleRgs);
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
		SubmissionStatusRenewal statusRenewal = new SubmissionStatusRenewal();
		statusRenewal.setCreatedBy(evaluation.getCreatedBy());
		statusRenewal.setIdAssignmentSubmission(evaluation.getIdAssignmentSubmission());
		statusRenewal.setIdSubmissionStatus(statusService.getByCode(SubmissionStatusCode.GRADED.code));
		statusRenewalService.insertSubmissionStatusRenewal(statusRenewal);
	}

	private void validateInsert(Evaluations evaluation) throws Exception {
		if (evaluation.getIdAssignmentSubmission() != null) {
			AssignmentSubmissions submission = assignmentSubmissionsService
					.getById(evaluation.getIdAssignmentSubmission().getId());
			if (submission == null) {
				throw new Exception("Id Assignment Submission salah");
			}
		} else {
			throw new Exception("Id Assignment Submission tidak boleh kosong");
		}
		if (evaluation.getScore() == null) {
			throw new Exception("Score tidak boleh kosong");
		}
	}

	private void validateUpdate(Evaluations evaluation) throws Exception {
		if (evaluation.getIdAssignmentSubmission() != null) {
			AssignmentSubmissions submission = assignmentSubmissionsService
					.getById(evaluation.getIdAssignmentSubmission().getId());
			if (submission == null) {
				throw new Exception("Id Assignment Submission salah");
			}
		} else {
			throw new Exception("Id Assignment Submission tidak boleh kosong");
		}
		if (evaluation.getScore() == null) {
			throw new Exception("Score tidak boleh kosong");
		}
	}

	private void sendEmail(Evaluations evaluation) throws Exception {
		AssignmentSubmissions assignmentSubmissions = assignmentSubmissionsService
				.getById(evaluation.getIdAssignmentSubmission().getId());
		evaluation.setIdAssignmentSubmission(assignmentSubmissions);
		Profiles participant = evaluationsDao.getParticipantProfile(evaluation);

		General general = generalService.getTemplateEmail(TemplateEmail.EVALUATION_PARTICIPANT.code);
		String text = general.getTemplateHtml();

		text = text.replace("#1#", participant.getFullName());

		MailHelper mailHelper = new MailHelper();
		mailHelper.setFrom("wedemy.id@gmail.com");
		mailHelper.setTo(participant.getEmail());
		mailHelper.setSubject(TemplateEmail.EVALUATION_PARTICIPANT.subject);
		mailHelper.setText(text);
		new MailServiceImpl(mailUtil, mailHelper).start();
	}

	@Override
	public List<?> reportAllScore(String idClass) throws Exception {
		List<?> data = evaluationsDao.reportAllScore(idClass);
		validateReport(data);
		return evaluationsDao.reportAllScore(idClass);
	}

	@Override
	public List<?> reportScore(String idDtlClass, String idParticipant) throws Exception {
		return evaluationsDao.reportScore(idDtlClass, idParticipant);
	}

	@Override
	public List<?> getCertificate(String idUser, String idDetailClass) throws Exception {
		List<?> data = evaluationsDao.getCertificate(idUser, idDetailClass);
		validateReport(data);
		return evaluationsDao.getCertificate(idUser, idDetailClass);
	}

	private void validateReport(List<?> data) throws Exception {
		if (data == null) {
			throw new Exception("Data kosong");
		}
	}

}
