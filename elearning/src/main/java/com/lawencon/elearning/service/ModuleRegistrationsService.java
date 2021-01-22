package com.lawencon.elearning.service;

import com.lawencon.elearning.helper.ClassesHelper;
import com.lawencon.elearning.model.ModuleRegistrations;

public interface ModuleRegistrationsService {
	
	void insertModuleRegistration(ClassesHelper clazzHelper) throws Exception;
	
	ModuleRegistrations getByIdClassAndIdModuleRegistration(String idClass, String idModRegist) throws Exception;
	
}
