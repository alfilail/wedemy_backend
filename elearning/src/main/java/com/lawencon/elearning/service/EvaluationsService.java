package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.model.Evaluations;

/**
 * @author Nur Alfilail
 */

public interface EvaluationsService {

	void insertEvaluation(Evaluations evaluation) throws Exception;

	List<Evaluations> getAllEvaluations() throws Exception;

	Evaluations getEvaluationById(String id) throws Exception;

	Evaluations getEvaluationByCode(String code) throws Exception;

}
