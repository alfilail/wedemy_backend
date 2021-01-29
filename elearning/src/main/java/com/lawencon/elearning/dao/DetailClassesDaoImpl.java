package com.lawencon.elearning.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.DetailClasses;
import com.lawencon.util.Callback;

@Repository
public class DetailClassesDaoImpl extends ElearningBaseDaoImpl<DetailClasses> implements DetailClassesDao {
	@Override
	public void insertDetailClass(DetailClasses detailClass, Callback before) throws Exception {
		save(detailClass, before, null);
	}

	@Override
	public List<DetailClasses> getAllDetailClass() throws Exception {
		return getAll();
	}

	@Override
	public DetailClasses getDetailClassById(String id) throws Exception {
		return getById(id);
	}
	
	@Override
	public DetailClasses getDetailClassByCode(String code) throws Exception {
		List<DetailClasses> detailClassList = 
				createQuery("FROM DetailClasses WHERE code = ?1 ", DetailClasses.class)
				.setParameter(1, code).getResultList();
		return detailClassList.size() > 0 ? detailClassList.get(0) : null;
	}
}