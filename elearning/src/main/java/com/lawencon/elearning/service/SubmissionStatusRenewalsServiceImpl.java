package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.SubmissionStatusRenewalsDao;
import com.lawencon.elearning.model.SubmissionStatusRenewal;

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
		statusRenewalDao.insertSubmissionStatusRenewal(statusRenewal, () -> validateInsert(statusRenewal));
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