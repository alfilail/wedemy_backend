package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.Approvements;
import com.lawencon.util.Callback;

public interface ApprovementsDao {

	void insert(Approvements approvement, Callback before) throws Exception;

	List<Approvements> getAllApprovement() throws Exception;

	Approvements getApprovementById(String id) throws Exception;

	void update(Approvements approvement, Callback before) throws Exception;

	void deleteApprovementById(String id) throws Exception;

	Approvements getByCode(String code) throws Exception;
	
	void softDeleteById(String id, String idUser) throws Exception;

	List<?> validateDelete(String id) throws Exception;

}
