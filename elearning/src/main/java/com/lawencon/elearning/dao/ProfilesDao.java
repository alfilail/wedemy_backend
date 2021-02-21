package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.Profiles;
import com.lawencon.util.Callback;

public interface ProfilesDao {

	void insert(Profiles profile, Callback before) throws Exception;

	void update(Profiles profile, Callback before) throws Exception;

	void deleteProfileById(String id) throws Exception;

	void softDeleteProfileById(String id, String idUser) throws Exception;

	Profiles getProfileById(String id) throws Exception;

	Profiles getProfileByEmail(String email) throws Exception;

	Profiles getProfileByIdNumber(String idNumber) throws Exception;

	List<Profiles> getAllProfiles() throws Exception;

}
