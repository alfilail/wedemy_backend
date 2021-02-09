package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.Classes;
import com.lawencon.util.Callback;

public interface ClassesDao {

	void insert(Classes clazz, Callback before) throws Exception;

	List<Classes> getAllClass() throws Exception;
	
	List<Classes> getAllInactive() throws Exception;

	Classes getClassById(String id) throws Exception;

	Classes getByCode(String code) throws Exception;

	void update(Classes clazz, Callback before) throws Exception;

	void deleteById(String id) throws Exception;
	
	void softDeleteById(String id, String idUser) throws Exception;
	
	void updateIsActive(String id, String idUser) throws Exception;
	
	Classes getInActiveById(String id) throws Exception;

}