package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.model.Modules;

public interface ModulesService {

	void insertModule(Modules module) throws Exception;

	List<Modules> getAllModules() throws Exception;
	
	Modules getModuleById(String id) throws Exception;
	
	Modules getModuleByCode(String code) throws Exception;
	
	void softDeleteModuleById(String id, String idUser) throws Exception;

	void updateModule(Modules module) throws Exception;

	void deleteModuleById(String id, String idUser) throws Exception;

}
