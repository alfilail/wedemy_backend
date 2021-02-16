package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.ApprovementsRenewalDao;
import com.lawencon.elearning.helper.TutorApprovementInputs;
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
	public void insertByParticipant(ApprovementsRenewal approvementsRenewal) throws Exception {
		approvementsRenewal.setIdApprovement(approvementService.getByCode(ApprovementCode.PENDING.code));
		approvementsRenewal.setTrxNumber(generateTrxNumber(TransactionNumberCode.APPROVEMENT_RENEWAL.code));
		approvementsRenewalDao.insertByParticipant(approvementsRenewal, () -> validateInsert(approvementsRenewal));
	}

	@Override
	public void insertByTutor(TutorApprovementInputs approvementRenewals) throws Exception {
		for (ApprovementsRenewal approvementsRenewal : approvementRenewals.getApprovementRenewals()) {
			approvementsRenewal
					.setIdApprovement(approvementService.getByCode(approvementsRenewal.getIdApprovement().getCode()));
			approvementsRenewal.setTrxNumber(generateTrxNumber(TransactionNumberCode.APPROVEMENT_RENEWAL.code));
			approvementsRenewalDao.insertByTutor(approvementsRenewal, () -> validateInsert(approvementsRenewal));
		}
	}

	@Override
	public ApprovementsRenewal getById(String id) throws Exception {
		return approvementsRenewalDao.getApprovementsRenewalById(id);
	}

	@Override
	public ApprovementsRenewal checkParticipantPresence(String idDtlModuleRgs, String idUser) throws Exception {
		return approvementsRenewalDao.checkParticipantPresence(idDtlModuleRgs, idUser);
	}

	@Override
	public List<ApprovementsRenewal> getAll() throws Exception {
		return approvementsRenewalDao.getAllApprovementRenewals();
	}

	@Override
	public List<ApprovementsRenewal> getAllParticipantPresences(String idDtlClass, String idDtlModuleRgs)
			throws Exception {
		return approvementsRenewalDao.getAllParticipantPresences(idDtlClass, idDtlModuleRgs);
	}

	@Override
	public List<?> getPresenceReport(String idDetailClass) throws Exception {
		return approvementsRenewalDao.getPresenceReport(idDetailClass);
	}

	private void validateInsert(ApprovementsRenewal approvementsRenewal) throws Exception {

	}

}
