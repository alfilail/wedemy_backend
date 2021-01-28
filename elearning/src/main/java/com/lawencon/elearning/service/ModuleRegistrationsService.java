package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.helper.ClassesHelper;
import com.lawencon.elearning.helper.ModuleAndListLearningMaterial;
import com.lawencon.elearning.model.ModuleRegistrations;

public interface ModuleRegistrationsService {

	void insertModuleRegistration(ClassesHelper clazzHelper) throws Exception;

	ModuleRegistrations getByIdDetailClassAndIdModuleRegistration(String idDtlClass, String idModRegist)
			throws Exception;

//	List<ModuleAndListLearningMaterial> getByIdClass(String idClass) throws Exception;

}
