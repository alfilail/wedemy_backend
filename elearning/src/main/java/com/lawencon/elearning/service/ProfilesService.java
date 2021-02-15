package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lawencon.elearning.model.Profiles;

public interface ProfilesService {

	void insert(Profiles profile) throws Exception;

	void update(Profiles profile, MultipartFile file) throws Exception;

	void deleteById(String id) throws Exception;

	void softDeleteById(String id, String idUser) throws Exception;

	Profiles getById(String id) throws Exception;

	Profiles getByCode(String code) throws Exception;

	Profiles getByEmail(String email) throws Exception;

	Profiles getByIdNumber(String idNumber) throws Exception;

	List<Profiles> getAll() throws Exception;

}
