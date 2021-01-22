package com.lawencon.elearning.dao;

import com.lawencon.elearning.model.DetailModuleRegistrations;
import com.lawencon.elearning.model.ModuleRegistrations;
import com.lawencon.util.Callback;

public interface DetailModuleRegistrationsDao {
	
	void insertDetailModuleRegistration(DetailModuleRegistrations dtlModRegist, Callback before) throws Exception;
	
	ModuleRegistrations getByIdClassAndIdModuleRegistration(String idClass, String idModRegist) throws Exception;

}
