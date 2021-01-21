package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.model.Classes;

public interface ClassesService {

	void insertClass(Classes clazz) throws Exception;

	List<Classes> getAllClasses() throws Exception;

	Classes getClassById(String id) throws Exception;

	Classes getClassByCode(String code) throws Exception;

	void updateClass(Classes clazz) throws Exception;

	void deleteClassById(String id) throws Exception;

}
