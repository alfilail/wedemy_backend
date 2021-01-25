package com.lawencon.elearning.dao;

import com.lawencon.elearning.model.ModuleRegistrations;
import com.lawencon.util.Callback;

public interface ModuleRegistrationsDao {
	
	void insertModuleRegistration(ModuleRegistrations moduleRegistration, Callback before) throws Exception;
	
	ModuleRegistrations getByIdDetailClassAndIdModuleRegistration(String idDtlClass, String idModRegist) throws Exception;
}
