package com.lawencon.elearning.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.Profiles;
import com.lawencon.util.Callback;

@Repository
public class ProfilesDaoImpl extends ElearningBaseDaoImpl<Profiles> implements ProfilesDao {

	@Override
	public void insertProfile(Profiles profile, Callback before) throws Exception {
		save(profile, before, null);
	}

	@Override
	public List<Profiles> getAllProfiles() throws Exception {
		return getAll();
	}

	@Override
	public Profiles getProfileById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public Profiles getProfileByCode(String code) throws Exception {
		Profiles profile = createQuery("FROM Profiles WHERE code = ?1 ", Profiles.class).setParameter(1, code).getSingleResult();
		return profile;
	}

	@Override
	public void updateProfile(Profiles profile, Callback before) throws Exception {
		save(profile, before, null, true, true);
	}

	@Override
	public void deleteProfileById(String id) throws Exception {
		deleteById(id);
	}
	
	@Override
	public Profiles getProfileByEmail(String email) throws Exception {
		Profiles profile = createQuery("FROM Profiles WHERE email = ?1 ", Profiles.class).setParameter(1, email).getSingleResult();
		return profile;
	}

}
