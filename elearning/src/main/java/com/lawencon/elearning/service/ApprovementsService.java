package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.model.Approvements;

/**
 * @author Nur Alfilail
 */

public interface ApprovementsService {

	void insert(Approvements approvement) throws Exception;

	List<Approvements> getAll() throws Exception;

	Approvements getById(String id) throws Exception;

	void update(Approvements approvement) throws Exception;

	void deleteById(String id, String idUser) throws Exception;

	Approvements getByCode(String code) throws Exception;
}
