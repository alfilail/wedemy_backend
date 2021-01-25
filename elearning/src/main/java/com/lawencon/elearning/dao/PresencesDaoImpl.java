package com.lawencon.elearning.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.Presences;
import com.lawencon.util.Callback;

@Repository
public class PresencesDaoImpl extends ElearningBaseDaoImpl<Presences> implements PresencesDao {

	@Override
	public void insertPresence(Presences presence, Callback before) throws Exception {
		save(presence, before, null, true, true);
	}

	@Override
	public List<Presences> getAllPresences() throws Exception {
		return getAll();
	}

	@Override
	public Presences getPresenceById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public void updatePresence(Presences presence, Callback before) throws Exception {
		save(presence, before, null, true, true);
	}

	@Override
	public void deletePresenceById(String id) throws Exception {
		deleteById(id);
	}

	@Override
	public Presences getPresenceByCode(String code) throws Exception {
		Presences presence = createQuery("FROM Presences WHERE code =?1", Presences.class).setParameter(1, code)
				.getSingleResult();
		return presence;
	}

}
