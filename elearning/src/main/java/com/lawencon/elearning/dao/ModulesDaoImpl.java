package com.lawencon.elearning.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.Modules;
import com.lawencon.util.Callback;

@Repository
public class ModulesDaoImpl extends ElearningBaseDaoImpl<Modules> implements ModulesDao {

	@Override
	public void insertModule(Modules module, Callback before) throws Exception {
		save(module, before, null, true ,true);
	}

	@Override
	public List<Modules> getAllModules() throws Exception {
		return getAll();
	}

	@Override
	public void updateModule(Modules module, Callback before) throws Exception {
		save(module, before, null, true, true);
	}

	@Override
	public void deleteModuleById(String id) throws Exception {
		deleteById(id);
	}

	@Override
	public Modules getModuleById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public Modules getModuleByCode(String code) throws Exception {
		List<Modules> module = createQuery("FROM Modules WHERE code = ?1 ", Modules.class).setParameter(1, code).getResultList();
		return module.size() > 0 ? module.get(0) : null;
	}

}
