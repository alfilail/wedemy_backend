package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.SubmissionStatusRenewalsDao;
import com.lawencon.elearning.model.SubmissionStatusRenewal;
import com.lawencon.elearning.util.SubmissionStatusCode;
import com.lawencon.elearning.util.TransactionNumberCode;

/**
 * @author Nur Alfilail
 */

@Service
public class SubmissionStatusRenewalsServiceImpl extends ElearningBaseServiceImpl
		implements SubmissionStatusRenewalsService {

	@Autowired
	private SubmissionStatusRenewalsDao statusRenewalDao;

	@Override
	public void insertSubmissionStatusRenewal(SubmissionStatusRenewal statusRenewal) throws Exception {
		statusRenewal.setTrxNumber(generateTrxNumber(TransactionNumberCode.SUBMISSION_STATUS_RENEWAL.code));
		if (statusRenewal.getIdSubmissionStatus().getCode().equals(SubmissionStatusCode.UPLOADED.code)) {
			statusRenewalDao.insertByParticipant(statusRenewal, () -> validateInsert(statusRenewal));
		} else {
			statusRenewalDao.insertByTutor(statusRenewal, () -> validateInsert(statusRenewal));
		}
	}

	@Override
	public List<SubmissionStatusRenewal> getAllSubmissionStatusRenewal() throws Exception {
		return statusRenewalDao.getAllSubmissionStatusRenewal();
	}

	@Override
	public SubmissionStatusRenewal getSubmissionStatusRenewalById(String id) throws Exception {
		return statusRenewalDao.getSubmissionStatusRenewalById(id);
	}

	private void validateInsert(SubmissionStatusRenewal statusRenewal) throws Exception {

	}

}
