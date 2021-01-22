package com.lawencon.elearning.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.DetailModuleRegistrations;
import com.lawencon.elearning.model.ModuleRegistrations;
import com.lawencon.util.Callback;

@Repository
public class DetailModuleRegistrationsDaoImpl extends ElearningBaseDaoImpl<DetailModuleRegistrations> implements DetailModuleRegistrationsDao {


	@Override
	public ModuleRegistrations getByIdClassAndIdModuleRegistration(String idClass, String idModRegist)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertDetailModuleRegistration(DetailModuleRegistrations dtlModRegist, Callback before) throws Exception {
		save(dtlModRegist, before, null, true, true);
	}

}
