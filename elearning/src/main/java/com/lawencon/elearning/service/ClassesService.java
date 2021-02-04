package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lawencon.elearning.helper.ClassesHelper;
import com.lawencon.elearning.model.Classes;

public interface ClassesService {

	void insertClass(ClassesHelper clazzHelper, MultipartFile file) throws Exception;

	List<Classes> getAllClasses() throws Exception;
	
	List<Classes> getAllInactiveClass() throws Exception;

	Classes getClassById(String id) throws Exception;

	Classes getClassByCode(String code) throws Exception;

	void updateClass(Classes clazz, MultipartFile file) throws Exception;

	void deleteClassById(String id, String idUser) throws Exception;
	
	void updateClassIsActive(String id, String idUser) throws Exception;
	
	Classes getInActiveClassById(String id) throws Exception;

}
