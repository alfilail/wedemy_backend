package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ModulesDao;
import com.lawencon.elearning.model.Modules;

@Service
public class ModulesServiceImpl extends BaseServiceImpl implements ModulesService {

	@Autowired
	private ModulesDao modulesDao;

	@Override
	public void insertModule(Modules module) throws Exception {
		modulesDao.insertModule(module, () -> validateInsert(module));
	}

	@Override
	public List<Modules> getAllModules() throws Exception {
		return modulesDao.getAllModules();
	}

	@Override
	public void updateModule(Modules module) throws Exception {
		modulesDao.updateModule(module, () -> validateUpdate(module));
	}

	@Override
	public void deleteModuleById(String id) throws Exception {
		modulesDao.deleteModuleById(id);
	}

	@Override
	public Modules getModuleById(String id) throws Exception {
		return modulesDao.getModuleById(id);
	}

	@Override
	public Modules getModuleByCode(String code) throws Exception {
		return modulesDao.getModuleByCode(code);
	}

	private void validateInsert(Modules module) throws Exception {
		if(module.getCode() == null || module.getCode().trim().equals("")) {
			throw new Exception("Kode Modul tidak boleh kosong");
		} else if(module.getModuleName() == null || module.getModuleName().trim().equals("")) {
			throw new Exception("Nama Modul tidak boleh kosong");
		}
	}

	private void validateUpdate(Modules module) throws Exception {
		if(module.getId() == null || module.getId().trim().equals("")) {
			throw new Exception("Id Modul tidak boleh kosong");
		} else if(module.getCode() == null || module.getCode().trim().equals("")) {
			throw new Exception("Kode Modul tidak boleh kosong");
		} else if(module.getModuleName() == null || module.getModuleName().trim().equals("")) {
			throw new Exception("Nama Modul tidak boleh kosong");
		}
	}
}
