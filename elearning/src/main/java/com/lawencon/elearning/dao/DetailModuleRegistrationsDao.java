package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.DetailModuleRegistrations;
import com.lawencon.util.Callback;

public interface DetailModuleRegistrationsDao {

	void insertDetailModuleRegistration(DetailModuleRegistrations dtlModRegist, Callback before) throws Exception;

	DetailModuleRegistrations getDetailModuleRegistrationsById(String id) throws Exception;

	DetailModuleRegistrations getByOrderNumber(Integer orderNumber) throws Exception;

	Integer totalHours(String idDtlClass) throws Exception;

	List<DetailModuleRegistrations> getDetailModuleRegistrationsByIdModuleRgs(String idModuleRgs) throws Exception;

	void deleteDetailModuleRegistration(String id, String idUser) throws Exception;

	DetailModuleRegistrations getDetailModuleRegistrationByIdLearningMaterial(String id) throws Exception;

	void update(DetailModuleRegistrations dtlModRegist, Callback before) throws Exception;

}
