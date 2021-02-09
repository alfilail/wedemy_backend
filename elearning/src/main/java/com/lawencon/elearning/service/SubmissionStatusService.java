package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.model.SubmissionStatus;

public interface SubmissionStatusService {
	void insert(SubmissionStatus submissionStatus) throws Exception;
	
	List<SubmissionStatus> getAll() throws Exception;
	
	SubmissionStatus getById (String id) throws Exception;
	
	void deleteById (String id, String idUser) throws Exception;
	
	void update(SubmissionStatus submissionStatus) throws Exception;
	
	SubmissionStatus getByCode(String code) throws Exception;
}
