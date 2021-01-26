package com.lawencon.elearning.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.ClassEnrollments;
import com.lawencon.util.Callback;

@Repository
public class ClassEnrollmentsDaoImpl extends ElearningBaseDaoImpl<ClassEnrollments> implements ClassEnrollmentsDao {
	@Override
	public void insertClassEnrollment(ClassEnrollments classEnrollment, Callback before) throws Exception {
		save(classEnrollment, before, null, true, true);
	}
	
	@Override
	public List<ClassEnrollments> getAllClassEnrollments() throws Exception {
		return getAll();
	}
	
	@Override
	public ClassEnrollments getclassEnrollmentByCode(String code) throws Exception {
		return null;
	}
	
	@Override
	public ClassEnrollments getClassEnrollmentById(String id) throws Exception {
		return getById(id);
	}
	
	@Override
	public void deleteclassEnrollmentById(String id) throws Exception {
		deleteById(id);
	}
	
	@Override
	public void updateClassEnrollment(ClassEnrollments classEnrollment, Callback before) throws Exception {
		save(classEnrollment, before, null, true, true);
	}
}
