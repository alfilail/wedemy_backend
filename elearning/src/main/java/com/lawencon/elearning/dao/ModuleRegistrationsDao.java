package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.ModuleRegistrations;
import com.lawencon.util.Callback;

public interface ModuleRegistrationsDao {

	void insertModuleRegistration(ModuleRegistrations moduleRegistration, Callback before) throws Exception;

	ModuleRegistrations getByIdDetailClassAndIdModuleRegistration(String idDtlClass, String idModRegist)
			throws Exception;

	List<ModuleRegistrations> getByIdDtlClass(String idDtlClass) throws Exception;
}
