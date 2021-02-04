package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.helper.ClassesHelper;
import com.lawencon.elearning.helper.ModuleAndLearningMaterials;
import com.lawencon.elearning.model.ModuleRegistrations;

public interface ModuleRegistrationsService {

	void insertModuleRegistration(ClassesHelper clazzHelper) throws Exception;

	ModuleRegistrations getByIdDetailClassAndIdModuleRegistration(String idDtlClass, String idModRegist)
			throws Exception;

	List<ModuleRegistrations> getByIdDtlClass(String idDtlClass) throws Exception;

	List<ModuleAndLearningMaterials> getModuleAndLearningMaterialsByIdDtlClass(String idUser, String idDtlClass) throws Exception;
	
	List<ModuleRegistrations> getModuleRegistrationsByIdDetailClass (String idDetailClass) throws Exception;
}
