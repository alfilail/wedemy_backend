package com.lawencon.elearning.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ModuleRegistrationsDao;
import com.lawencon.elearning.helper.ClassesHelper;
import com.lawencon.elearning.helper.ModuleAndListLearningMaterial;
import com.lawencon.elearning.model.ModuleRegistrations;
import com.lawencon.elearning.model.Modules;

@Service
public class ModuleRegistrationsServiceImpl extends BaseServiceImpl implements ModuleRegistrationsService {

	@Autowired
	private ModuleRegistrationsDao moduleRegistrationDao;

	@Autowired
	private DetailModuleRegistrationsService dtlModuleRgsService;

	@Override
	public void insertModuleRegistration(ClassesHelper clazzHelper) throws Exception {
		System.out.println("module : "+clazzHelper.getModule());
		Modules[] modulesList = clazzHelper.getModule();
		for (Modules modules : modulesList) {
			ModuleRegistrations moduleRegistrations = new ModuleRegistrations();
			moduleRegistrations.setCreatedAt(LocalDateTime.now());
			moduleRegistrations.setIdDetailClass(clazzHelper.getDetailClass());
			moduleRegistrations.setIdModule(modules);
			moduleRegistrationDao.insertModuleRegistration(moduleRegistrations,
					() -> validateInsert(moduleRegistrations));
		}
	}

	@Override
	public ModuleRegistrations getByIdDetailClassAndIdModuleRegistration(String idDtlClass, String idModRegist)
			throws Exception {
		return moduleRegistrationDao.getByIdDetailClassAndIdModuleRegistration(idDtlClass, idModRegist);
	}

//	@Override
//	public List<ModuleAndListLearningMaterial> getByIdClass(String idClass) throws Exception {
//		List<ModuleAndListLearningMaterial> listResult = new ArrayList<>();
//		List<ModuleRegistrations> moduleRgsList = moduleRegistrationDao.getByIdClass(idClass);
//		for (ModuleRegistrations moduleRgs : moduleRgsList) {
//			ModuleAndListLearningMaterial result = new ModuleAndListLearningMaterial();
//			result.setModule(moduleRgs);
//			result.setLearningMaterialList(
//					dtlModuleRgsService.getDetailModuleRegistrationsByIdModuleRgs(moduleRgs.getId()));
//		}
//		return listResult;
//	}

	private void validateInsert(ModuleRegistrations moduleRegistration) {

	}

}
