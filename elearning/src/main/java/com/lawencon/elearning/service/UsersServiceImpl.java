package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.UsersDao;
import com.lawencon.elearning.model.Users;

@Service
public class UsersServiceImpl extends BaseServiceImpl implements UsersService {
	
	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void insertUser(Users user) throws Exception {
		user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
		usersDao.insertUser(user, () -> validateInsert(user));
	}

	@Override
	public List<Users> getAllUsers() throws Exception {
		return usersDao.getAllUsers();
	}

	@Override
	public Users getUserById(String id) throws Exception {
		return usersDao.getUserById(id);
	}

	@Override
	public Users getUserByUsername(String username) throws Exception {
		return usersDao.getUserByUsername(username);
	}

	@Override
	public void updateUser(Users user) throws Exception {
		usersDao.updateUser(user, () -> validateUpdate(user));
	}

	@Override
	public void deleteUserById(String id) throws Exception {
		usersDao.deleteUserById(id);
	}
	
	private void validateInsert(Users user) {

	}

	private void validateUpdate(Users user) {

	}

}
