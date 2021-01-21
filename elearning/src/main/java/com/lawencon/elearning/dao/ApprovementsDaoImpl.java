package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.elearning.model.Approvements;
import com.lawencon.util.Callback;

public class ApprovementsDaoImpl extends BaseDaoImpl<Approvements> implements ApprovementsDao {

	@Override
	public void insertApprovement(Approvements approvement, Callback before) throws Exception {
		save(approvement, before, null, true, true);
	}

	@Override
	public List<Approvements> getAllApprovements() throws Exception {
		return getAll();
	}

	@Override
	public Approvements getApprovementsById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public void updateApprovement(Approvements approvement, Callback before) throws Exception {
		save(approvement, before, null, true, true);
	}

	@Override
	public void deleteApprovementById(String id) throws Exception {
		deleteById(id);
	}

	@Override
	public Approvements getApprovementByCode(String code) throws Exception {
		Approvements approvement = createQuery("FROM Approvements WHERE code = ?1", Approvements.class)
				.setParameter(1, code).getSingleResult();
		return approvement;
	}

}