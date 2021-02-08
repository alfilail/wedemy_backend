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
	private FilesService filesService;

//	@Autowired
//	private GeneralService generalService;

	@Override
	public void insertProfile(Profiles profile) throws Exception {
//		byte[] defaultPict = generalService.getDefaultPict("rgs");
//		String pic = defaultPict.toString();
		Files file = new Files();
		file.setFile(null);
		file.setType(null);
		filesService.insertFile(file);
		profile.setIdFile(file);
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
		try {
			begin();	
			Files profilePict = filesService.getFileById(profile.getIdFile().getId());
			if (file != null && !file.isEmpty()) {
				profilePict.setFile(file.getBytes());
				profilePict.setType(file.getContentType());
				filesService.updateFile(profilePict);
				profile.setIdFile(profilePict);
			} else {
				profile.setIdFile(profilePict);
			}
			profilesDao.updateProfile(profile, () -> {
				validateUpdate(profile);
			});
			commit();
		} catch (Exception e) {
			rollback();
			throw new Exception(e);
		}
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
		} else {
//			Profiles pfl = getProfileById(profile.getId());
			if (profile.getFullName() == null || profile.getFullName().trim().equals("")) {
				throw new Exception("Nama Lengkap tidak boleh kosong");
			}
//			if (pfl.getVersion().equals(profile.getVersion())) {
//				throw new Exception("Profile yang diedit telah diperbarui, silahkan coba lagi");
//			}
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

}
