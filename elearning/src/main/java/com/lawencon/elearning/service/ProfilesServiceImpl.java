package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ProfilesDao;
import com.lawencon.elearning.model.Profiles;

@Service
public class ProfilesServiceImpl extends BaseServiceImpl implements ProfilesService {
	
	@Autowired
	private ProfilesDao profilesDao;

	@Override
	public void insertProfile(Profiles profile) throws Exception {
		profilesDao.insertProfile(profile, () -> validateInsert(profile));
	}

	@Override
	public List<Profiles> getAllProfiles() throws Exception {
		return profilesDao.getAllProfiles();
	}

	@Override
	public Profiles getProfileById(String id) throws Exception {
		return profilesDao.getProfileById(id);
	}

	@Override
	public Profiles getProfileByCode(String code) throws Exception {
		return profilesDao.getProfileByCode(code);
	}

	@Override
	public void updateProfile(Profiles profile) throws Exception {
		profilesDao.updateProfile(profile, () -> validateUpdate(profile));
	}

	@Override
	public void deleteProfileById(String id) throws Exception {
		profilesDao.deleteProfileById(id);
	}
	
	private void validateInsert(Profiles profile) {

	}

	private void validateUpdate(Profiles profile) {

	}

}
