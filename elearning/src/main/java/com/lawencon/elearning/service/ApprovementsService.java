package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.model.Approvements;

/**
 * @author Nur Alfilail
 */

public interface ApprovementsService {

	void insertApprovement(Approvements approvement) throws Exception;

	List<Approvements> getAllApprovements() throws Exception;

	Approvements getApprovementsById(String id) throws Exception;

	void updateApprovement(Approvements approvement) throws Exception;

	void deleteApprovementById(String id) throws Exception;

	Approvements getApprovementByCode(String code) throws Exception;
}
