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

	@Override
	public Profiles getProfileByEmail(String email) throws Exception {
		return profilesDao.getProfileByEmail(email);
	}

	@Override
	public Profiles getProfileByIdNumber(String idNumber) throws Exception {
		return profilesDao.getProfileByIdNumber(idNumber);
	}

	private void validateInsert(Profiles profile) throws Exception {

	}

	private void validateUpdate(Profiles profile) throws Exception {
		if (profile.getId() == null || profile.getId().trim().equals("")) {
			throw new Exception("Id tidak boleh kosong");
		} else if (profile.getFullName() == null || profile.getFullName().trim().equals("")) {
			throw new Exception("Nama Lengkap tidak boleh kosong");
		} else if (profile.getIdNumber() == null || profile.getIdNumber().trim().equals("")) {
			throw new Exception("Nomor Kartu Penduduk tidak boleh kosong");
		} else if (profile.getBirthPlace() == null || profile.getBirthPlace().trim().equals("")) {
			throw new Exception("Tempat Lahir tidak boleh kosong");
		} else if (profile.getBirthDate() == null || profile.getBirthDate().toString().trim().equals("")) {
			throw new Exception("Tanggal Lahir tidak boleh kosong");
		} else if (profile.getEmail() == null || profile.getEmail().trim().equals("")) {
			throw new Exception("Email tidak boleh kosong");
		} else if (profile.getPhone() == null || profile.getPhone().trim().equals("")) {
			throw new Exception("Nomor Handphone tidak boleh kosong");
		} else if (profile.getAddress() == null || profile.getAddress().trim().equals("")) {
			throw new Exception("Alamat tidak boleh kosong");
		}
	}

}
