package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.Profiles;
import com.lawencon.elearning.model.Users;
import com.lawencon.util.Callback;

public interface UsersDao {

	void insert(Users user, Callback before) throws Exception;

	List<Users> getAllUser() throws Exception;

	Users getUserById(String id) throws Exception;

	Users getByUsername(String username) throws Exception;

	void update(Users user, Callback before) throws Exception;

	void deleteUserById(String id) throws Exception;

	Users getByIdProfile(Profiles profile) throws Exception;

	List<Users> getByRoleCode(String code) throws Exception;

	Users getByIdDetailClass(String idDtlClass) throws Exception;

	void softDeleteById(String id, String idUser) throws Exception;

	List<?> validateDelete(String id) throws Exception;

	Users getByIdNumber(String idNumber) throws Exception;
	
	Users getByIdClass(String idClass) throws Exception;
}
