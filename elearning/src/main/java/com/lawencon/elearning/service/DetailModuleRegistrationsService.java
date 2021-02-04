package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.model.DetailModuleRegistrations;

public interface DetailModuleRegistrationsService {

	void insertDetailModuleRegistration(DetailModuleRegistrations dtlModRegist) throws Exception;

	List<DetailModuleRegistrations> getDetailModuleRegistrationsByIdModuleRgs(String idModuleRgs) throws Exception;

	DetailModuleRegistrations getDetailModuleRegistrationsById(String id) throws Exception;
	
	void deleteDetailModuleRegistration(String id, String idUser) throws Exception;
	
	DetailModuleRegistrations getDetailModuleRegistrationByIdLearningMaterial(String id) throws Exception;

}
