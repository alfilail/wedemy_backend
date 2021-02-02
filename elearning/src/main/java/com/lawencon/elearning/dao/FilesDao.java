package com.lawencon.elearning.dao;

import com.lawencon.elearning.model.Files;
import com.lawencon.util.Callback;

/**
 * @author Nur Alfilail
 */

public interface FilesDao {

	void insertFile(Files file, Callback before) throws Exception;

	Files getFileById(String id) throws Exception;

}
