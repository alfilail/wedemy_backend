package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.model.ClassEnrollments;
import com.lawencon.elearning.model.DetailClasses;

public interface ClassEnrollmentService {
	void insertClassEnrollment(ClassEnrollments classEnrollment) throws Exception;

	List<ClassEnrollments> getAllClassEnrollments() throws Exception;

	ClassEnrollments getClassEnrollmentsById(String id) throws Exception;

	List<DetailClasses> getAllClassEnrollmentsByIdUser(String id) throws Exception;

	ClassEnrollments getClassEnrollmentByIdDtlClassAndIdUser(String idDtlClass, String idUser);

	void deleteClassEnrollmentsById(String id) throws Exception;

	void updateClassEnrollments(ClassEnrollments classEnrollment) throws Exception;

	ClassEnrollments getClassEnrollmentByCode(String code) throws Exception;

	Integer getTotalParticipantsByIdDtlClass(String id) throws Exception;

	List<?> getCertificate(String idUser, String idClass) throws Exception;
}
