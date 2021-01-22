package com.lawencon.elearning.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.Roles;
import com.lawencon.util.Callback;

@Repository
public class RolesDaoImpl extends ElearningBaseDaoImpl<Roles> implements RolesDao {
	@Override
	public void insertRole(Roles role, Callback before) throws Exception {
		save(role, before, null, true, true);
	}
	
	@Override
	public Roles getRoleById(String id) throws Exception {
		return getById(id);
	}
	
	@Override
	public Roles getRoleByCode(String code) throws Exception {
		Roles role = createQuery("FROM Roles WHERE code = ?1", Roles.class)
				.setParameter(1, code).getSingleResult();
		return role;
	}
	
	@Override
	public List<Roles> getAllRoles() throws Exception {
		return getAll();
	}
	
	@Override
	public void updateRole(Roles role, Callback before) throws Exception {
		save(role, before, null, true, true);
	}
	
	@Override
	public void deleteRoleById(String id) throws Exception {
		deleteById(id);
	}
}
