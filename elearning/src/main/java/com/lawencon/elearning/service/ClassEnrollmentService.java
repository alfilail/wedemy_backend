package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.model.ClassEnrollments;

public interface ClassEnrollmentService {
	void insertClassEnrollment(ClassEnrollments classEnrollment) throws Exception;
	
	List<ClassEnrollments> getAllClassEnrollments() throws Exception;
	
	ClassEnrollments getClassEnrollmentsById (String id) throws Exception;
	
	void deleteClassEnrollmentsById (String id) throws Exception;
	
	void updateClassEnrollments(ClassEnrollments classEnrollment) throws Exception;
	
	ClassEnrollments getClassEnrollmentByCode(String code) throws Exception;
}
