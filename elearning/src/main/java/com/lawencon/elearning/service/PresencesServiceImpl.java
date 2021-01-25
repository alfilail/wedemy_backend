package com.lawencon.elearning.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.PresencesDao;
import com.lawencon.elearning.model.Approvements;
import com.lawencon.elearning.model.Presences;

/**
 * @author Nur Alfilail
 */

@Service
public class PresencesServiceImpl extends BaseServiceImpl implements PresencesService {

	@Autowired
	private PresencesDao presencesDao;

	@Autowired
	private ApprovementsService approvementsService;

	@Override
	public void insertPresence(Presences presence) throws Exception {
		Approvements approvement = approvementsService.getApprovementByCode(presence.getIdApprovement().getCode());
		presence.setIdApprovement(approvement);
		presence.setPresenceTime(LocalTime.now());
		presencesDao.insertPresence(presence, () -> validateInsert(presence));
	}

	@Override
	public List<Presences> getAllPresences() throws Exception {
		return presencesDao.getAllPresences();
	}

	@Override
	public Presences getPresenceById(String id) throws Exception {
		return presencesDao.getPresenceById(id);
	}

	@Override
	public void updatePresence(Presences presence) throws Exception {
		Approvements approvement = approvementsService.getApprovementByCode(presence.getIdApprovement().getCode());
		presence.setIdApprovement(approvement);
		presencesDao.updatePresence(presence, () -> validateUpdate(presence));
	}

	@Override
	public void deletePresenceById(String id) throws Exception {
		presencesDao.deletePresenceById(id);
	}

	@Override
	public Presences getPresenceByCode(String code) throws Exception {
		return presencesDao.getPresenceByCode(code);
	}

	private void validateInsert(Presences presence) throws Exception {

	}

	private void validateUpdate(Presences presence) throws Exception {

	}

}
