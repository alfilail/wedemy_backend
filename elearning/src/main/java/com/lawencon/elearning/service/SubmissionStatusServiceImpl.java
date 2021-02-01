package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.SubmissionStatusDao;
import com.lawencon.elearning.model.SubmissionStatus;

@Service
public class SubmissionStatusServiceImpl implements SubmissionStatusService {

	@Autowired
	private SubmissionStatusDao submissionStatusDao;

	@Override
	public void insertSubmissionStatus(SubmissionStatus submissionStatus) throws Exception {
		submissionStatusDao.insertSubmissionStatus(submissionStatus, () -> validateInsert(submissionStatus));
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
		submissionStatusDao.updateSubmissionStatus(submissionStatus, () -> validateUpdate(submissionStatus));
	}

	private void validateInsert(SubmissionStatus submissionStatus) throws Exception {
		if (submissionStatus.getSubmissionStatusName() == null
				|| submissionStatus.getSubmissionStatusName().trim().equals("")) {
			throw new Exception("Nama status tidak boleh kosong");
		}
		if (submissionStatus.getCode() == null || submissionStatus.getCode().trim().equals("")) {
			throw new Exception("Kode status tidak boleh kosong");
		}
	}

	private void validateUpdate(SubmissionStatus submissionStatus) throws Exception {
		if (submissionStatus.getId() == null || submissionStatus.getId().trim().equals("")) {
			throw new Exception("Id status tidak boleh kosong");
		}
		if (submissionStatus.getSubmissionStatusName() == null
				|| submissionStatus.getSubmissionStatusName().trim().equals("")) {
			throw new Exception("Nama status tidak boleh kosong");
		}
		if (submissionStatus.getCode() == null || submissionStatus.getCode().trim().equals("")) {
			throw new Exception("Kode status tidak boleh kosong");
		}
	}
}
