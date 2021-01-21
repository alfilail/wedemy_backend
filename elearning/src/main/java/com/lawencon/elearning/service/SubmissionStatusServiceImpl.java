package com.lawencon.elearning.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.SubmissionStatusDao;
import com.lawencon.elearning.model.SubmissionStatus;

@Service
public class SubmissionStatusServiceImpl implements SubmissionStatusService{
	@Autowired
	private SubmissionStatusDao submissionStatusDao;
	
	@Override
	public void insertSubmissionStatus(SubmissionStatus submissionStatus) throws Exception {
		submissionStatus.setCreatedAt(LocalDateTime.now());
		submissionStatusDao.insertSubmissionStatus(submissionStatus, 
				()-> validateInsert(submissionStatus));
	}
	
	@Override
	public SubmissionStatus getSubmissionStatusById(String id) throws Exception {
		return submissionStatusDao.getSubmissionStatusById(id);
	}
	
	@Override
	public SubmissionStatus getSubmissionStatusByCode(String code) throws Exception {
		return submissionStatusDao.getSubmissionStatusByCode(code);
	}
	
	@Override
	public List<SubmissionStatus> getAllSubmissionStatus() throws Exception {
		return submissionStatusDao.getAllSubmissionStatus();
	}
	
	@Override
	public void deleteSubmissionStatusById(String id) throws Exception {
		submissionStatusDao.deleteSubmissionStatusById(id);
		
	}
	
	@Override
	public void updateSubmissionStatus(SubmissionStatus submissionStatus) throws Exception {
		submissionStatusDao.updateSubmissionStatus(submissionStatus, 
				()-> validateUpdate(submissionStatus));
	}
	
	private void validateInsert(SubmissionStatus submissionStatus) throws Exception {
		if (submissionStatus.getCode() == null) {
			throw new Exception("Invalid Input Submission Status Code");
		}
	}
	
	private void validateUpdate(SubmissionStatus submissionStatus) throws Exception {
		if (submissionStatus.getCode() == null) {
			throw new Exception("Invalid Input Submission Status Code");
		}
	}
}
