package com.lawencon.elearning.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.PresencesDao;
import com.lawencon.elearning.model.Approvements;
import com.lawencon.elearning.model.ApprovementsRenewal;
import com.lawencon.elearning.model.Presences;
import com.lawencon.elearning.model.Users;

/**
 * @author Nur Alfilail
 */

@Service
public class PresencesServiceImpl extends ElearningBaseServiceImpl implements PresencesService {

	@Autowired
	private PresencesDao presencesDao;
	
	@Autowired
	private ApprovementsService approvementsService;
	
	@Autowired
	private ApprovementsRenewalService approvementsRenewalService;
	
	@Autowired
	private UsersService usersService;

	@Override
	public void insertPresence(Presences presence) throws Exception {
		presence.setTrxNumber(generateTrxNumber());
		presence.setPresenceTime(LocalTime.now());
		presencesDao.insertPresence(presence, () -> validateInsert(presence));
		Users user = usersService.getUserById(presence.getIdUser().getId());
		if(user.getIdRole().getCode() == "std") {
			insertApprovementRenewal(presence);
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
	
	private void insertApprovementRenewal(Presences presence) throws Exception {
		Approvements approvements = approvementsService.getApprovementByCode("PNDG");
		ApprovementsRenewal approvementsRenewal = new ApprovementsRenewal();
		approvementsRenewal.setIdPresence(presence);
		approvementsRenewal.setIdApprovement(approvements);
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
		public List<?> getPresenceReport() throws Exception {
			return presencesDao.getPresenceReport();
		}

	private void validateInsert(Presences presence) throws Exception {

	}

	private void validateUpdate(Presences presence) throws Exception {

	}
	
	private String generateTrxNumber() {
		Random random = new Random();
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yy-MM-dd");
		String formattedDate = localDate.format(myFormat);
		String trxCodeValue = String.valueOf(random.nextInt((999 + 1 - 100) + 100));
		String trx = bBuilder(formattedDate).toString();
		trx = trx.replaceAll("-", "");
		String trxNumber= bBuilder("ASB-", trx, "-",trxCodeValue).toString();
		return trxNumber;
	}

}
