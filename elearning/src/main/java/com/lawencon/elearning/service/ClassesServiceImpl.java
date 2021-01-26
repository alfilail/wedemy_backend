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
import com.lawencon.elearning.model.DetailClasses;

@Service
public class ClassesServiceImpl extends BaseServiceImpl implements ClassesService {

	@Autowired
	private ClassesDao classesDao;

	@Autowired
	private ModuleRegistrationsService moduleRegistrationsService;

	@Autowired
	private DetailClassesService detailClassesService;

	@Override
	public void insertClass(ClassesHelper helper, MultipartFile file) throws Exception {
		if (helper.getClazz().getCode() != null) {
			Classes clazz = helper.getClazz();
			clazz.setCreatedAt(LocalDateTime.now());
			clazz.setThumbnailImg(file.getBytes());
			clazz.setFileType(file.getContentType());
			classesDao.insertClass(clazz, () -> validateInsert(clazz));
			DetailClasses detailClass = helper.getDetailClass();
			detailClass.setIdClass(clazz);
			helper.setDetailClass(detailClass);
		}
		detailClassesService.insertDetailClass(helper);
		moduleRegistrationsService.insertModuleRegistration(helper);
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
		clazz.setThumbnailImg(file.getBytes());
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
