package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lawencon.elearning.helper.ClassesHelper;
import com.lawencon.elearning.model.Classes;

public interface ClassesService {

	void insert(ClassesHelper clazzHelper, MultipartFile file) throws Exception;

	List<Classes> getAll() throws Exception;
	
	List<Classes> getAllInactive() throws Exception;

	Classes getById(String id) throws Exception;

	Classes getByCode(String code) throws Exception;

	void update(Classes clazz, MultipartFile file) throws Exception;

	void deleteById(String id, String idUser) throws Exception;
	
	void updateIsActive(String id, String idUser) throws Exception;
	
	Classes getInActiveById(String id) throws Exception;
	
	List<Integer> getTotalClassAndUser() throws Exception;

}
