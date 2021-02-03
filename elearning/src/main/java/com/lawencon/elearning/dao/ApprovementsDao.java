package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.Approvements;
import com.lawencon.util.Callback;

public interface ApprovementsDao {

	void insertApprovement(Approvements approvement, Callback before) throws Exception;

	List<Approvements> getAllApprovements() throws Exception;

	Approvements getApprovementsById(String id) throws Exception;

	void updateApprovement(Approvements approvement, Callback before) throws Exception;

	void deleteApprovementById(String id) throws Exception;

	Approvements getApprovementByCode(String code) throws Exception;
	
	void softDeleteApprovementById(String id, String idUser) throws Exception;

	List<?> validateDeleteApprovement(String id) throws Exception;

}
