package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.SubmissionStatusRenewal;
import com.lawencon.util.Callback;

/**
 * @author Nur Alfilail
 */

public interface SubmissionStatusRenewalsDao {

	void insertByParticipant(SubmissionStatusRenewal statusRenewal, Callback before) throws Exception;
	
	void insertByTutor(SubmissionStatusRenewal statusRenewal, Callback before) throws Exception;

	List<SubmissionStatusRenewal> getAllSubmissionStatusRenewal() throws Exception;

	SubmissionStatusRenewal getSubmissionStatusRenewalById(String id) throws Exception;
}
