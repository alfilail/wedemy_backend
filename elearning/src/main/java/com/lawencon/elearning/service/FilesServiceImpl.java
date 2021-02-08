package com.lawencon.elearning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.FilesDao;
import com.lawencon.elearning.model.Files;

/**
 * @author Nur Alfilail
 */

@Service
public class FilesServiceImpl extends BaseServiceImpl implements FilesService {

	@Autowired
	private FilesDao filesDao;

	@Override
	public void insertFile(Files file) throws Exception {
		filesDao.insertFile(file, () -> validateInsert(file));
	}

	@Override
	public Files getFileById(String id) throws Exception {
		return filesDao.getFileById(id);
	}

	@Override
	public void updateFile(Files file) throws Exception {
		filesDao.updateFile(file, () -> validateUpdate(file));
	}
	
	private void validateInsert(Files file) throws Exception {

	}
	
	private void validateUpdate(Files file) throws Exception {

	}


}
