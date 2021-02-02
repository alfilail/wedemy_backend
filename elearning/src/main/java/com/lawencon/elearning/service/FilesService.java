package com.lawencon.elearning.service;

import com.lawencon.elearning.model.Files;

/**
 * @author Nur Alfilail
 */

public interface FilesService {

	void insertFile(Files file) throws Exception;

	Files getFileById(String id) throws Exception;
}
