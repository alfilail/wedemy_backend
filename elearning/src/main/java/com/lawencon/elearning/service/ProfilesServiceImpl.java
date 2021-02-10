package com.lawencon.elearning.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	@Override
	public void insert(Profiles profile) throws Exception {
		Files file = new Files();
		file.setFile(null);
		file.setType(null);
		filesService.insert(file);
		profile.setIdFile(file);
		profilesDao.insert(profile, () -> validateInsert(profile));
	}

	@Override
	public List<Profiles> getAll() throws Exception {
		return profilesDao.getAllProfile();
	}

	@Override
	public Profiles getById(String id) throws Exception {
		return profilesDao.getProfileById(id);
	}

	@Override
	public Profiles getByCode(String code) throws Exception {
		return profilesDao.getByCode(code);
	}

	@Override
	public void update(Profiles profile, MultipartFile file) throws Exception {
		try {
			begin();	
			Files profilePict = filesService.getById(profile.getIdFile().getId());
			if (file != null && !file.isEmpty()) {
				profilePict.setFile(file.getBytes());
				profilePict.setType(file.getContentType());
				profilePict.setCreatedAt(profilePict.getCreatedAt());
				profilePict.setCreatedBy(profilePict.getCreatedBy());
				filesService.update(profilePict);
				profile.setIdFile(profilePict);
			} else {
				profile.setIdFile(profilePict);
			}
			Profiles pfl = getById(profile.getId());
			profile.setCreatedAt(pfl.getCreatedAt());
			profile.setCreatedBy(pfl.getCreatedBy());
			profilesDao.update(profile, () -> {
				validateUpdate(profile);
			});
			commit();
		} catch (Exception e) {
			rollback();
			throw new Exception(e);
		}
	}

	@Override
	public void deleteById(String id) throws Exception {
		profilesDao.deleteById(id);
	}

	@Override
	public void softDeleteById(String id, String idUser) throws Exception {
		profilesDao.softDeleteById(id, idUser);
	}

	@Override
	public Profiles getByEmail(String email) throws Exception {
		return profilesDao.getByEmail(email);
	}

	@Override
	public Profiles getByIdNumber(String idNumber) throws Exception {
		return profilesDao.getByIdNumber(idNumber);
	}

	private void validateInsert(Profiles profile) throws Exception {

	}

	private void validateUpdate(Profiles profile) throws Exception {
		if (profile.getId() == null || profile.getId().trim().equals("")) {
			throw new Exception("Id tidak boleh kosong");
		} else {
			Profiles pfl = getById(profile.getId());
			if (profile.getFullName() == null || profile.getFullName().trim().equals("")) {
				throw new Exception("Nama Lengkap tidak boleh kosong");
			}
//			if (pfl.getVersion() != profile.getVersion()) {
//				throw new Exception("Profile yang diedit telah diperbarui, silahkan coba lagi");
//			}
			if (profile.getIdFile() != null) {
				if(profile.getIdFile().getType() != null) {
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
			if (profile.getPhone() != null) {
				if (profile.getPhone().length() <= 10 && profile.getPhone().length() >= 13) {
					Pattern pattern = Pattern.compile("\\d{10, 13}");
					Matcher matcher = pattern.matcher(profile.getPhone());
					if (matcher.matches()) {
						throw new Exception("Nomor handphone tidak sesuai!");
					}
				}
			}
			if (profile.getIdNumber() != null) {
				if (profile.getIdNumber().length() > 16) {
					throw new Exception("Kartu penduduk tidak sesuai!");
				}
			}
		}
	}

}
