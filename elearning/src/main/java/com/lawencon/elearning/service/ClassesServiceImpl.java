package com.lawencon.elearning.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ClassesDao;
import com.lawencon.elearning.helper.ClassesHelper;
import com.lawencon.elearning.model.Classes;

@Service
public class ClassesServiceImpl extends BaseServiceImpl implements ClassesService {

	@Autowired
	private ClassesDao classesDao;
	
	@Autowired
	private ModuleRegistrationsService moduleRegistrationsService;

	@Override
	public void insertClass(ClassesHelper clazzHelper, MultipartFile file) throws Exception {
		Classes clazz = clazzHelper.getClazz();
		clazz.setCreatedAt(LocalDateTime.now());
		clazz.setThubmnailImg(file.getBytes());
		clazz.setFileType(file.getContentType());
		classesDao.insertClass(clazz, () -> validateInsert(clazz));
		moduleRegistrationsService.insertModuleRegistration(clazzHelper);
	}

	@Override
	public List<Classes> getAllClasses() throws Exception {
		return classesDao.getAllClasses();
	}

	@Override
	public Classes getClassById(String id) throws Exception {
		return classesDao.getClassById(id);
	}

	@Override
	public Classes getClassByCode(String code) throws Exception {
		return classesDao.getClassByCode(code);
	}

	@Override
	public void updateClass(Classes clazz, MultipartFile file) throws Exception {
		clazz.setThubmnailImg(file.getBytes());
		clazz.setFileType(file.getContentType());
		classesDao.updateClass(clazz, () -> validateUpdate(clazz));
	}

	@Override
	public void deleteClassById(String id) throws Exception {
		classesDao.deleteClassById(id);
	}

	private void validateInsert(Classes clazz) {

	}

	private void validateUpdate(Classes clazz) {

	}

}
