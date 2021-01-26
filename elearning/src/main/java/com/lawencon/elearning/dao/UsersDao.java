package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.Profiles;
import com.lawencon.elearning.model.Users;
import com.lawencon.util.Callback;

public interface UsersDao {

	void insertUser(Users user, Callback before) throws Exception;

	List<Users> getAllUsers() throws Exception;

	Users getUserById(String id) throws Exception;

	Users getUserByUsername(String username) throws Exception;

	void updateUser(Users user, Callback before) throws Exception;

	void deleteUserById(String id) throws Exception;
	
	Users getUserByIdProfile(Profiles profile) throws Exception;

}
