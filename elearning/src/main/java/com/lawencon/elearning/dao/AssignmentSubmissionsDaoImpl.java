package com.lawencon.elearning.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.AssignmentSubmissions;
import com.lawencon.util.Callback;

@Repository
public class AssignmentSubmissionsDaoImpl extends ElearningBaseDaoImpl<AssignmentSubmissions> implements AssignmentSubmissionsDao {
	@Override
	public void insertAssignmentSubmission(AssignmentSubmissions assignmentSubmission, Callback before)
			throws Exception {
		save(assignmentSubmission, before, null, true, true);
	}
	
	@Override
	public List<AssignmentSubmissions> getAllAssignmentSubmissions() throws Exception {
		return getAll();
	}
	
	@Override
	public AssignmentSubmissions getAssignmentSubmissionByCode(String code) throws Exception {
		return null;
	}
	
	@Override
	public AssignmentSubmissions getAssignmentSubmissionById(String id) throws Exception {
		return getById(id);
	}
	
	@Override
	public void updateAssignmentSubmission(AssignmentSubmissions assignmentSubmission, Callback before)
			throws Exception {
		save(assignmentSubmission, before, null, true, true);
	}
	
	@Override
	public void deleteAssignmentSubmissionById(String id) throws Exception {
		deleteById(id);
	}
}