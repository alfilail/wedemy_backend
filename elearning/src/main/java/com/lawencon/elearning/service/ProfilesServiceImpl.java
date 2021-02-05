package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ProfilesDao;
import com.lawencon.elearning.model.Files;
import com.lawencon.elearning.model.Profiles;

@Service
public class ProfilesServiceImpl extends BaseServiceImpl implements ProfilesService {

	@Autowired
	private ProfilesDao profilesDao;

	@Autowired
	FilesService filesService;

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
	public void updateProfile(Profiles profile, MultipartFile file) throws Exception {
		Files profilePict = filesService.getFileById(profile.getIdFile().getId());
		profile.setIdFile(profilePict);
		profilesDao.updateProfile(profile, () -> {
			validateUpdate(profile);
			filesService.insertFile(profilePict);
		});
	}

	@Override
	public void deleteProfileById(String id) throws Exception {
		profilesDao.deleteProfileById(id);
	}

	@Override
	public void softDeleteProfileById(String id, String idUser) throws Exception {
		profilesDao.softDeleteProfileById(id, idUser);
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
		}
		if (profile.getFullName() == null || profile.getFullName().trim().equals("")) {
			throw new Exception("Nama Lengkap tidak boleh kosong");
		}
		if (profile.getIdFile() != null) {
			String[] type = profile.getIdFile().getType().split("/");
			String ext = type[1];
			if (ext != null) {
				if (ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpeg")) {

				} else {
					throw new Exception("File harus gambar");
				}
			}
		}
	}

}
