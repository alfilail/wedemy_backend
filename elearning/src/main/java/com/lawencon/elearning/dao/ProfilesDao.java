package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.Profiles;
import com.lawencon.util.Callback;

public interface ProfilesDao {

	void insert(Profiles profile, Callback before) throws Exception;

	List<Profiles> getAllProfile() throws Exception;

	Profiles getProfileById(String id) throws Exception;

	Profiles getByCode(String code) throws Exception;

	void update(Profiles profile, Callback before) throws Exception;

	void deleteById(String id) throws Exception;

	Profiles getByEmail(String email) throws Exception;

	Profiles getByIdNumber(String idNumber) throws Exception;
	
	void softDeleteById(String id, String idUser) throws Exception;
}
