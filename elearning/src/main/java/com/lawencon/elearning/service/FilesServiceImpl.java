package com.lawencon.elearning.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.FilesDao;
import com.lawencon.elearning.model.Files;

/**
 * @author Nur Alfilail
 */

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

	private void validateInsert(Files file) throws Exception {

	}

}
