package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.AssignmentSubmissions;
import com.lawencon.util.Callback;

public interface AssignmentSubmissionsDao {
	void insertAssignmentSubmission(AssignmentSubmissions assignmentSubmission, Callback before) throws Exception;
	
	List<AssignmentSubmissions> getAllAssignmentSubmissions() throws Exception;
	
	AssignmentSubmissions getAssignmentSubmissionById(String id) throws Exception;
	
	AssignmentSubmissions getAssignmentSubmissionByCode(String code) throws Exception;
	
	String getTutorEmail(AssignmentSubmissions assignmentSubmission) throws Exception;
	
	void updateAssignmentSubmission(AssignmentSubmissions assignmentSubmission, Callback before) throws Exception;
	
	void deleteAssignmentSubmissionById(String id) throws Exception;

}
