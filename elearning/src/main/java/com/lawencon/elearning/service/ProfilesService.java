package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.model.Profiles;

public interface ProfilesService {
	
	void insertProfile(Profiles profile) throws Exception;

	List<Profiles> getAllProfiles() throws Exception;

	Profiles getProfileById(String id) throws Exception;

	Profiles getProfileByCode(String code) throws Exception;

	void updateProfile(Profiles profile) throws Exception;

	void deleteProfileById(String id) throws Exception;

}
