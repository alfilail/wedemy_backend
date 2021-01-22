package com.lawencon.elearning.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.ModuleRegistrations;
import com.lawencon.util.Callback;

@Repository
public class ModuleRegistrationsDaoImpl extends ElearningBaseDaoImpl<ModuleRegistrations> implements ModuleRegistrationsDao{
	@Override
	public void insertModuleRegistration(ModuleRegistrations moduleRegistration, Callback before) throws Exception {
		save(moduleRegistration, before, null, true, true);
	}
}
