package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.helper.ClassInput;
import com.lawencon.elearning.helper.EnrolledClass;
import com.lawencon.elearning.helper.ModuleAndLearningMaterials;
import com.lawencon.elearning.model.ModuleRegistrations;

public interface ModuleRegistrationsService {

	void insertModuleRegistration(ClassInput clazzHelper) throws Exception;

	ModuleRegistrations getByIdDetailClassAndIdModuleRegistration(String idDtlClass, String idModRegist)
			throws Exception;

	List<ModuleRegistrations> getByIdDtlClass(String idDtlClass) throws Exception;

	EnrolledClass getModuleAndLearningMaterialsByIdDtlClass(String idUser, String idDtlClass) throws Exception;
	
	List<ModuleRegistrations> getModuleRegistrationsByIdDetailClass (String idDetailClass) throws Exception;
}
