package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.Evaluations;
import com.lawencon.util.Callback;

/**
 * @author Nur Alfilail
 */

public interface EvaluationsDao {

	void insertEvaluation(Evaluations evaluation, Callback before) throws Exception;

	List<Evaluations> getAllEvaluations() throws Exception;

	Evaluations getEvaluationById(String id) throws Exception;

	Evaluations getEvaluationByCode(String code) throws Exception;

	String getParticipantEmail(Evaluations evaluation) throws Exception;

}
