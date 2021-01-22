package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.RolesDao;
import com.lawencon.elearning.model.Roles;

@Service
public class RolesServiceImpl extends BaseServiceImpl implements RolesService{
	@Autowired
	private RolesDao rolesDao;
	
	@Override
	public void insertRole(Roles role) throws Exception {
		rolesDao.insertRole(role, ()-> validateInsert(role));
	}
	
	@Override
	public Roles getRoleById(String id) throws Exception {
		return rolesDao.getRoleById(id);
	}
	
	@Override
	public Roles getRoleByCode(String code) throws Exception {
		return rolesDao.getRoleByCode(code);
	}
	
	@Override
	public List<Roles> getAllRoles() throws Exception {
		return rolesDao.getAllRoles();
	}
	
	@Override
	public void updateRole(Roles role) throws Exception {
		rolesDao.updateRole(role, ()->validateUpdate(role));
	}
	
	@Override
	public void deleteRoleById(String id) throws Exception {
		rolesDao.deleteRoleById(id);
	}
	
	private void validateInsert(Roles role) throws Exception {
		if (role.getCode() == null) {
			throw new Exception("Invalid Input Role Code");
		}
	}
	
	private void validateUpdate(Roles role) throws Exception {
		if (role.getCode() == null) {
			throw new Exception("Invalid Input Role Code");
		}
	}
}
