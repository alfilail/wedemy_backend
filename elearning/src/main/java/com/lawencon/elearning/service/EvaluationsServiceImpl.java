package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
				if (evaluation.getIdAssignmentSubmission().getId() != null) {
					Evaluations existedEval = evaluationsDao.getByIdDtlModuleRgsAndIdParticipant(
							evaluation.getIdAssignmentSubmission().getIdDetailModuleRegistration().getId(),
							evaluation.getIdAssignmentSubmission().getIdParticipant().getId());
					if (existedEval == null) {
						Grades grade = gradesService.getGradeByScore(evaluation.getScore());
						evaluation.setIdGrade(grade);
						evaluationsDao.insertEvaluation(evaluation, () -> validateInsert(evaluation));
						insertStatusRenewal(evaluation);
//						System.out.println("Sending Email...");
//						sendEmail(evaluation);
//						System.out.println("Done");
					} else if (existedEval != null
							&& evaluation.getScore().doubleValue() != existedEval.getScore().doubleValue()) {
						Grades grade = gradesService.getGradeByScore(evaluation.getScore());
						evaluation.setIdGrade(grade);
						evaluationsDao.insertEvaluation(evaluation, () -> validateInsert(evaluation));
					}
				}
			}
			commit();
		} catch (Exception e) {
			rollback();
			throw new Exception(e);
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
		statusRenewal.setIdAssignmentSubmission(evaluation.getIdAssignmentSubmission());
		statusRenewal.setIdSubmissionStatus(statusService.getSubmissionStatusByCode("GRD"));
		statusRenewalService.insertSubmissionStatusRenewal(statusRenewal);
	}

	private void validateInsert(Evaluations evaluation) throws Exception {

	}

	private void sendEmail(Evaluations evaluation) throws Exception {
		AssignmentSubmissions assignmentSubmissions = assignmentSubmissionsService
				.getAssignmentSubmissionsById(evaluation.getIdAssignmentSubmission().getId());
		evaluation.setIdAssignmentSubmission(assignmentSubmissions);
		Profiles participant = evaluationsDao.getParticipantProfile(evaluation);

		General general = generalService.getTemplateEmail("scrupd");
		String text = general.getTemplateHtml();

		text = text.replace("#1#", participant.getFullName());

		MailHelper mailHelper = new MailHelper();
		mailHelper.setFrom("elearningalfione@gmail.com");
		mailHelper.setTo(participant.getEmail());
		mailHelper.setSubject("Score of Your Assignment Submission Has Updated");
		mailHelper.setText(text);
		new MailServiceImpl(mailUtil, mailHelper).start();
	}

	@Override
	public List<?> reportAllScore(String idClass) throws Exception {
//		List<?> data = evaluationsDao.reportAllScore(idClass);
//		validateReport(data);
		return evaluationsDao.reportAllScore(idClass);
	}

	@Override
	public List<?> reportScore(String idDtlClass, String idParticipant) throws Exception {
		return evaluationsDao.reportScore(idDtlClass, idParticipant);
	}

//	private void validateReport(List<?> data) throws Exception {
//		if(data == null) {
//			throw new Exception("Data kosong");
//		}
//	}

}
