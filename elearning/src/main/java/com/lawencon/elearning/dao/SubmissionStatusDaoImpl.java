package com.lawencon.elearning.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.SubmissionStatus;
import com.lawencon.util.Callback;

@Repository
public class SubmissionStatusDaoImpl extends ElearningBaseDaoImpl<SubmissionStatus> implements SubmissionStatusDao {

	@Override
	public void insertSubmissionStatus(SubmissionStatus submissionStatus, Callback before) throws Exception {
		save(submissionStatus, before, null, true, true);
	}

	@Override
	public SubmissionStatus getSubmissionStatusById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public SubmissionStatus getSubmissionStatusByCode(String code) throws Exception {
		List<SubmissionStatus> submissionStatus = createQuery("FROM SubmissionStatus WHERE code = ?1", SubmissionStatus.class)
				.setParameter(1, code).getResultList();
		return resultCheck(submissionStatus);
	}

	@Override
	public List<SubmissionStatus> getAllSubmissionStatus() throws Exception {
		return getAll();
	}

	@Override
	public void deleteSubmissionStatusById(String id) throws Exception {
		deleteById(id);
	}

	@Override
	public void updateSubmissionStatus(SubmissionStatus submissionStatus, Callback before) throws Exception {
		save(submissionStatus, before, null, true, true);
	}
}