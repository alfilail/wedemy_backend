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
		Profiles profile = createQuery("FROM Profiles WHERE code = ?1 ", Profiles.class).setParameter(1, code)
				.getSingleResult();
		return profile;
	}

	@Override
	public Profiles getProfileByIdNumber(String idNumber) throws Exception {
		List<Profiles> listResult = createQuery("FROM Profiles WHERE id_number = ?1 ", Profiles.class)
				.setParameter(1, idNumber).getResultList();
		return listResult.size() > 0 ? listResult.get(0) : null;
	}

	@Override
	public void updateProfile(Profiles profile, Callback before) throws Exception {
		save(profile, before, null);
	}

	@Override
	public void deleteProfileById(String id) throws Exception {
		deleteById(id);
	}

	@Override
	public Profiles getProfileByEmail(String email) throws Exception {
		List<Profiles> profile = createQuery("FROM Profiles WHERE email = ?1 ", Profiles.class).setParameter(1, email)
				.getResultList();
		return resultCheck(profile);
	}
	
	@Override
	public void softDeleteProfileById(String id, String idUser) throws Exception {
		updateNativeSQL("UPDATE t_m_profiles SET is_active = false", id, idUser);
	}

}
