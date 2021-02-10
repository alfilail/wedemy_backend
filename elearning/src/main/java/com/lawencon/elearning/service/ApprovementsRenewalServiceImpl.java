package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.ApprovementsRenewalDao;
import com.lawencon.elearning.model.ApprovementsRenewal;
import com.lawencon.elearning.util.ApprovementCode;
import com.lawencon.elearning.util.TransactionNumberCode;

@Service
public class ApprovementsRenewalServiceImpl extends ElearningBaseServiceImpl implements ApprovementsRenewalService {

	@Autowired
	private ApprovementsRenewalDao approvementsRenewalDao;

	@Autowired
	private ApprovementsService approvementService;

	@Override
	public void insertApprovementsRenewal(ApprovementsRenewal approvementsRenewal) throws Exception {
		approvementsRenewal.setIdApprovement(approvementService.getByCode(ApprovementCode.PENDING.code));
		approvementsRenewal.setTrxNumber(generateTrxNumber(TransactionNumberCode.APPROVEMENT_RENEWAL.code));
		approvementsRenewalDao.insertApprovementsRenewal(approvementsRenewal,
				() -> validateInsert(approvementsRenewal));
	}

	@Override
	public void participantApprovementsRenewal(ApprovementsRenewal approvementsRenewal) throws Exception {
		approvementsRenewal.setIdApprovement(
				approvementService.getByCode(approvementsRenewal.getIdApprovement().getCode()));
		approvementsRenewalDao.participantApprovementsRenewal(approvementsRenewal,
				() -> validateInsert(approvementsRenewal));
	}

	@Override
	public List<ApprovementsRenewal> getAllApprovementsRenewal() throws Exception {
		return approvementsRenewalDao.getAllApprovementsRenewal();
	}

	@Override
	public List<ApprovementsRenewal> getListParticipantsPresence(String idDtlClass, String idDtlModuleRgs)
			throws Exception {
		return approvementsRenewalDao.getListParticipantsPresence(idDtlClass, idDtlModuleRgs);
	}

	@Override
	public ApprovementsRenewal getApprovementsRenewalById(String id) throws Exception {
		return approvementsRenewalDao.getApprovementsRenewalById(id);
	}
	
	@Override
	public List<?> getPresenceReport(String idDetailClass) throws Exception {
		return approvementsRenewalDao.getPresenceReport(idDetailClass);
	}

	@Override
	public ApprovementsRenewal checkParticipantPresence(String idDtlModuleRgs, String idUser) throws Exception {
		return approvementsRenewalDao.checkParticipantPresence(idDtlModuleRgs, idUser);
	}

	private void validateInsert(ApprovementsRenewal approvementsRenewal) throws Exception {

	}

}
