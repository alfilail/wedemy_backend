package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.Modules;
import com.lawencon.util.Callback;

public interface ModulesDao {

	void insert(Modules module, Callback before) throws Exception;

	List<Modules> getAllModule() throws Exception;

	Modules getModuleById(String id) throws Exception;

	Modules getByCode(String code) throws Exception;

	void update(Modules module, Callback before) throws Exception;

	void deleteById(String id) throws Exception;
	
	void softDeleteById(String id, String idUser) throws Exception;

	List<?> validateDelete(String id) throws Exception;

}