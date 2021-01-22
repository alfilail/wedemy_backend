package com.lawencon.elearning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ModuleRegistrationsDao;
import com.lawencon.elearning.helper.ClassesHelper;
import com.lawencon.elearning.model.ModuleRegistrations;
import com.lawencon.elearning.model.Modules;

@Service
public class ModuleRegistrationsServiceImpl extends BaseServiceImpl implements ModuleRegistrationsService {
	@Autowired
	private ModuleRegistrationsDao moduleRegistrationDao;
	
	@Override
	public void insertModuleRegistration(ClassesHelper clazzHelper) throws Exception {
		Modules[] modulesList =  clazzHelper.getModule();
		for(Modules modules : modulesList) {
			ModuleRegistrations moduleRegistrations = new ModuleRegistrations();
			moduleRegistrations.setIdClass(clazzHelper.getClazz());
			moduleRegistrations.setIdModule(modules);
			moduleRegistrationDao.insertModuleRegistration(moduleRegistrations, ()-> validateInsert(moduleRegistrations));
		}
	}
	
	private void validateInsert(ModuleRegistrations moduleRegistration) {

	}

	@Override
	public ModuleRegistrations getByIdClassAndIdModuleRegistration(String idClass, String idModRegist)
			throws Exception {
		return moduleRegistrationDao.getByIdClassAndIdModuleRegistration(idClass, idModRegist);
	}

//	private void validateUpdate(ModuleRegistrations moduleRegistration) {
//
//	}
}
