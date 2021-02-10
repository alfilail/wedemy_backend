package com.lawencon.elearning.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.PresencesDao;
import com.lawencon.elearning.model.ApprovementsRenewal;
import com.lawencon.elearning.model.Presences;
import com.lawencon.elearning.model.Users;
import com.lawencon.elearning.util.RoleCode;
import com.lawencon.elearning.util.TransactionNumberCode;

/**
 * @author Nur Alfilail
 */

@Service
public class PresencesServiceImpl extends ElearningBaseServiceImpl implements PresencesService {

	@Autowired
	private PresencesDao presencesDao;

	@Autowired
	private ApprovementsRenewalService approvementsRenewalService;

	@Autowired
	private UsersService usersService;

	@Override
	public void insertPresence(Presences presence) throws Exception {
		try {
			begin();
			presence.setTrxNumber(generateTrxNumber(TransactionNumberCode.PRESENCES.code));
			presence.setPresenceTime(LocalTime.now());
			presencesDao.insertPresence(presence, () -> validateInsert(presence));
			Users user = usersService.getById(presence.getIdUser().getId());
			if (user.getIdRole().getCode().equals(RoleCode.PARTICIPANT.code)) {
				insertApprovementRenewal(presence);
			}
			commit();
		} catch (Exception e) {
			rollback();
			throw new Exception(e);
		}
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
	public Presences doesTutorPresent(String idDtlModuleRgs) throws Exception {
		return presencesDao.doesTutorPresent(idDtlModuleRgs);
	}

	@Override
	public Presences doesParticipantPresent(String idDtlModuleRgs, String idParticipant) throws Exception {
		return presencesDao.doesParticipantPresent(idDtlModuleRgs, idParticipant);
	}

	private void insertApprovementRenewal(Presences presence) throws Exception {
		ApprovementsRenewal approvementsRenewal = new ApprovementsRenewal();
		approvementsRenewal.setIdPresence(presence);
		approvementsRenewalService.insertApprovementsRenewal(approvementsRenewal);
	}

	@Override
	public void updatePresence(Presences presence) throws Exception {
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

	@Override
	public List<?> getPresenceReport(String idDetailClass)
			throws Exception {
		return presencesDao.getPresenceReport(idDetailClass);
	}

	private void validateInsert(Presences presence) throws Exception {

	}

	private void validateUpdate(Presences presence) throws Exception {

	}

}
