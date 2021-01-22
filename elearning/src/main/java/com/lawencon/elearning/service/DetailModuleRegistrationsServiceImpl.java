package com.lawencon.elearning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.DetailModuleRegistrationsDao;
import com.lawencon.elearning.model.DetailModuleRegistrations;

@Service
public class DetailModuleRegistrationsServiceImpl extends BaseServiceImpl implements DetailModuleRegistrationsService {
	
	@Autowired
	private DetailModuleRegistrationsDao dtlModRegistDao;

	@Override
	public void insertDetailModuleRegistration(DetailModuleRegistrations dtlModRegist) throws Exception {
		dtlModRegistDao.insertDetailModuleRegistration(dtlModRegist, () -> validateInput(dtlModRegist));
	}
	
	private void validateInput(DetailModuleRegistrations dtlModRegist) {
		
	}

}
