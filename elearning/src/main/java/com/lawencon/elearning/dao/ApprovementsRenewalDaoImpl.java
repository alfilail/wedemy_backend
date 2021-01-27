package com.lawencon.elearning.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.ApprovementsRenewal;
import com.lawencon.util.Callback;

@Repository
public class ApprovementsRenewalDaoImpl extends ElearningBaseDaoImpl<ApprovementsRenewal> implements ApprovementsRenewalDao {
	@Override
	public void insertApprovementsRenewal(ApprovementsRenewal approvementsRenewal, Callback before) throws Exception {
		save(approvementsRenewal, before, null, true, true);
	}
	
	@Override
	public List<ApprovementsRenewal> getAllApprovementsRenewal() throws Exception {
		return getAll();
	}
	
	@Override
	public ApprovementsRenewal getApprovementsRenewalById(String id) throws Exception {
		return getById(id);
	}
}
