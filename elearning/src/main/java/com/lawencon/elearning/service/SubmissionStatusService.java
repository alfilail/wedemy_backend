package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.model.SubmissionStatus;

public interface SubmissionStatusService {
	void insertSubmissionStatus(SubmissionStatus submissionStatus) throws Exception;
	
	List<SubmissionStatus> getAllSubmissionStatus() throws Exception;
	
	SubmissionStatus getSubmissionStatusById (String id) throws Exception;
	
	void deleteSubmissionStatusById (String id, String idUser) throws Exception;
	
	void updateSubmissionStatus(SubmissionStatus submissionStatus) throws Exception;
	
	SubmissionStatus getSubmissionStatusByCode(String code) throws Exception;
}
