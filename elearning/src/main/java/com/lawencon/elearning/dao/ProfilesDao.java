package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.Profiles;
import com.lawencon.util.Callback;

public interface ProfilesDao {
	
	void insertProfile(Profiles profile, Callback before) throws Exception;

	List<Profiles> getAllProfiles() throws Exception;

	Profiles getProfileById(String id) throws Exception;

	Profiles getProfileByCode(String code) throws Exception;

	void updateProfile(Profiles profile, Callback before) throws Exception;

	void deleteProfileById(String id) throws Exception;

	Profiles getProfileByEmail(String email) throws Exception;
}
