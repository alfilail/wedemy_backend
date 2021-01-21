package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.SubmissionStatus;
import com.lawencon.util.Callback;

public interface SubmissionStatusDao {
	void insertSubmissionStatus(SubmissionStatus submissionStatus, Callback before) throws Exception;
	
	List<SubmissionStatus> getAllSubmissionStatus() throws Exception;
	
	SubmissionStatus getSubmissionStatusById(String id) throws Exception;
	
	void updateSubmissionStatus(SubmissionStatus submissionStatus, Callback before) throws Exception;
	
	void deleteSubmissionStatusById(String id) throws Exception;
	
	SubmissionStatus getSubmissionStatusByCode(String code) throws Exception;
}
