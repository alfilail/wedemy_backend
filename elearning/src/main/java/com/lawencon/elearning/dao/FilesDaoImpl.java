package com.lawencon.elearning.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.Files;
import com.lawencon.util.Callback;

/**
 * @author Nur Alfilail
 */

@Repository
public class FilesDaoImpl extends ElearningBaseDaoImpl<Files> implements FilesDao {

	@Override
	public void insert(Files file, Callback before) throws Exception {
		save(file, before, null);
	}

	@Override
	public Files getFileById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public void update(Files file, Callback before) throws Exception {
		save(file, before, null);
	}

}
