package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.Classes;
import com.lawencon.util.Callback;

public interface ClassesDao {

	void insertClass(Classes clazz, Callback before) throws Exception;

	List<Classes> getAllClasses() throws Exception;

	Classes getClassById(String id) throws Exception;

	Classes getClassByCode(String code) throws Exception;

	void updateClass(Classes clazz, Callback before) throws Exception;

	void deleteClassById(String id) throws Exception;
	
	void softDeleteClassById(String id, String idUser) throws Exception;

	List<?> validateDeleteClass(String id) throws Exception;

}