package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lawencon.elearning.model.AssignmentSubmissions;

public interface AssignmentSubmissionsService {
	void insertAssignmentSubmissions(AssignmentSubmissions assignmentSubmission, MultipartFile file) throws Exception;

	List<AssignmentSubmissions> getAllAssignmentSubmissions() throws Exception;

	AssignmentSubmissions getByIdDtlModuleRgsAndIdParticipant(String idDtlModuleRgs, String idParticipant)
			throws Exception;

	AssignmentSubmissions getAssignmentSubmissionsById(String id) throws Exception;

	AssignmentSubmissions getAssignmentSubmissionsByCode(String code) throws Exception;

	void updateAssignmentSubmission(AssignmentSubmissions assignmentSubmission, MultipartFile file) throws Exception;

	void deleteAssignmentSubmissionsById(String id) throws Exception;
}
