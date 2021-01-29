package com.lawencon.elearning.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.Classes;
import com.lawencon.util.Callback;

@Repository
public class ClassesDaoImpl extends ElearningBaseDaoImpl<Classes> implements ClassesDao{

	@Override
	public void insertClass(Classes clazz, Callback before) throws Exception {
		save(clazz, before, null);
	}

	@Override
	public List<Classes> getAllClasses() throws Exception {
		return getAll();
	}

	@Override
	public Classes getClassById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public void updateClass(Classes clazz, Callback before) throws Exception {
		save(clazz, before, null, true, true);
	}

	@Override
	public void deleteClassById(String id) throws Exception {
		deleteById(id);
	}

	@Override
	public Classes getClassByCode(String code) throws Exception {
		List<Classes> clsList = createQuery("FROM Classes WHERE code = ?1 ", Classes.class)
				.setParameter(1, code).getResultList();
		return clsList.size() > 0 ? clsList.get(0) : null;
	}

}