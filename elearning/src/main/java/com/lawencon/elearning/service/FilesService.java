package com.lawencon.elearning.service;

import com.lawencon.elearning.model.Files;

/**
 * @author Nur Alfilail
 */

public interface FilesService {

	void insert(Files file) throws Exception;

	void update(Files file) throws Exception;
	
	Files getById(String id) throws Exception;
	
}
