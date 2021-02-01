package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.ClassEnrollments;
import com.lawencon.util.Callback;

public interface ClassEnrollmentsDao {
	void insertClassEnrollment(ClassEnrollments classEnrollment, Callback before) throws Exception;

	List<ClassEnrollments> getAllClassEnrollments() throws Exception;

	ClassEnrollments getClassEnrollmentById(String id) throws Exception;

	List<ClassEnrollments> getAllClassEnrollmentsByIdUser(String id) throws Exception;

	ClassEnrollments getClassEnrollmentByIdDtlClassAndIdUser(String idDtlClass, String idUser);

	void updateClassEnrollment(ClassEnrollments classEnrollment, Callback before) throws Exception;

	void deleteclassEnrollmentById(String id) throws Exception;

	ClassEnrollments getclassEnrollmentByCode(String code) throws Exception;

	Integer getTotalParticipantsByIdDtlClass(String id) throws Exception;

	List<?> getCertificate(String idUser, String idClass) throws Exception;
}
