package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.helper.ScoreInputs;
import com.lawencon.elearning.model.Evaluations;

/**
 * @author Nur Alfilail
 */

public interface EvaluationsService {

	void insertEvaluation(ScoreInputs scores) throws Exception;

	void updateEvaluation(ScoreInputs scores) throws Exception;

	List<Evaluations> getAllEvaluations() throws Exception;

	List<Evaluations> getAllByIdDtlClassAndIdDtlModuleRgs(String idDtlClass, String idDtlModuleRgs) throws Exception;

	Evaluations getEvaluationById(String id) throws Exception;

	Evaluations getEvaluationByCode(String code) throws Exception;

	List<?> reportAllScore(String idClass) throws Exception;

	List<?> reportScore(String idDtlClass, String idParticipant) throws Exception;
	
	List<?> getCertificate(String idUser, String idDetailClass) throws Exception;
	
//	boolean checkCertificate(List<?> data) throws Exception;
}
