package com.lawencon.elearning.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.Profiles;
import com.lawencon.elearning.model.Users;
import com.lawencon.util.Callback;

@Repository
public class UsersDaoImpl extends ElearningBaseDaoImpl<Users> implements UsersDao {	

	@Override
	public void insertUser(Users user, Callback before) throws Exception {
		save(user, before, null, true, true);
	}

	@Override
	public List<Users> getAllUsers() throws Exception {
		return getAll();
	}

	@Override
	public Users getUserById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public Users getUserByUsername(String username) throws Exception {
		Users user = createQuery("FROM Users WHERE username = ?1 ", Users.class).setParameter(1, username).getSingleResult();
		return user;
	}

	@Override
	public void updateUser(Users user, Callback before) throws Exception {
		save(user, before, null, true, true);
	}

	@Override
	public void deleteUserById(String id) throws Exception {
		deleteById(id);
	}
	
	@Override
	public Users getUserByIdProfile(Profiles profile) throws Exception {
		Users user = createQuery("FROM Users WHERE idProfile.id = ?1 ", Users.class).setParameter(1, profile.getId()).getSingleResult();
		return user;
	}

}
