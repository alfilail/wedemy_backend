package com.lawencon.elearning.dao;

import com.lawencon.elearning.model.Files;
import com.lawencon.util.Callback;

/**
 * @author Nur Alfilail
 */

public class FilesDaoImpl extends ElearningBaseDaoImpl<Files> implements FilesDao {

	@Override
	public void insertFile(Files file, Callback before) throws Exception {
		save(file, before, null);
	}

	@Override
	public Files getFileById(String id) throws Exception {
		return getById(id);
	}

}
