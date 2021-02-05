package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lawencon.elearning.model.Profiles;

public interface ProfilesService {

	void insertProfile(Profiles profile) throws Exception;

	List<Profiles> getAllProfiles() throws Exception;

	Profiles getProfileById(String id) throws Exception;

	Profiles getProfileByCode(String code) throws Exception;

	void updateProfile(Profiles profile, MultipartFile file) throws Exception;

	void deleteProfileById(String id) throws Exception;
	
	void softDeleteProfileById(String id, String idUser) throws Exception;

	Profiles getProfileByEmail(String email) throws Exception;

	Profiles getProfileByIdNumber(String idNumber) throws Exception;

}
