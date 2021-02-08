package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.AssignmentSubmissions;
import com.lawencon.elearning.model.Profiles;
import com.lawencon.util.Callback;

public interface AssignmentSubmissionsDao {

	void insertAssignmentSubmission(AssignmentSubmissions assignmentSubmission, Callback before) throws Exception;

	List<AssignmentSubmissions> getAllAssignmentSubmissions() throws Exception;

	List<AssignmentSubmissions> getAllByIdDtlModuleRgs(String idDtlModuleRgs) throws Exception;

	AssignmentSubmissions getAssignmentSubmissionById(String id) throws Exception;

	AssignmentSubmissions getAssignmentSubmissionByCode(String code) throws Exception;

	Profiles getTutorProfile(AssignmentSubmissions assignmentSubmission) throws Exception;

	Profiles getParticipantProfile(AssignmentSubmissions assignmentSubmission) throws Exception;

	void updateAssignmentSubmission(AssignmentSubmissions assignmentSubmission, Callback before) throws Exception;

	void deleteAssignmentSubmissionById(String id) throws Exception;

	AssignmentSubmissions getByIdDtlModuleRgsAndIdParticipant(String idDtlModuleRgs, String idParticipant)
			throws Exception;

}
