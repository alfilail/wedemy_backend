package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.model.Users;

public interface UsersService {
	
	void insertUser(Users user) throws Exception;

	List<Users> getAllUsers() throws Exception;

	Users getUserById(String id) throws Exception;

	Users getUserByUsername(String username) throws Exception;

	void updateUser(Users user) throws Exception;

	void deleteUserById(String id) throws Exception;

}