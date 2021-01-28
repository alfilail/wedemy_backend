package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.DetailClassesDao;
import com.lawencon.elearning.helper.ClassesHelper;
import com.lawencon.elearning.model.DetailClasses;

@Service
public class DetailClassesServiceImpl extends BaseServiceImpl implements DetailClassesService {
	@Autowired
	private DetailClassesDao detailClassesDao;

	@Autowired
	ClassesService classService;

	@Override
	public void insertDetailClass(ClassesHelper clazzHelper) throws Exception {
		DetailClasses detailClass = clazzHelper.getDetailClass();
		detailClassesDao.insertDetailClass(detailClass, () -> validateInsert(detailClass));
	}

	private void validateInsert(DetailClasses detailClass) {

	}

	@Override
	public List<DetailClasses> getAllDetailClass() throws Exception {
		return detailClassesDao.getAllDetailClass();
	}

	@Override
	public DetailClasses getDetailClassById(String id) throws Exception {
		return detailClassesDao.getDetailClassById(id);
	}
}