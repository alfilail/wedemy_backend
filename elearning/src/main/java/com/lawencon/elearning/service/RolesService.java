package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.model.Roles;

public interface RolesService {
	void insertRole(Roles role) throws Exception;
	
	List<Roles> getAllRoles() throws Exception;
	
	Roles getRoleById(String id) throws Exception;
	
	void updateRole(Roles role) throws Exception;
	
	void deleteRoleById(String id) throws Exception;
	
	Roles getRoleByCode(String code) throws Exception;
}
