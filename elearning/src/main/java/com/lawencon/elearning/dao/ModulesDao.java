package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.Modules;
import com.lawencon.util.Callback;

public interface ModulesDao {

	void insertModule(Modules module, Callback before) throws Exception;

	List<Modules> getAllModules() throws Exception;

	Modules getModuleById(String id) throws Exception;

	Modules getModuleByCode(String code) throws Exception;

	void updateModule(Modules module, Callback before) throws Exception;

	void deleteModuleById(String id) throws Exception;
	
	void updateIsActive(String id, String idUser) throws Exception;

}