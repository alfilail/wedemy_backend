package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.ApprovementsRenewal;
import com.lawencon.util.Callback;

public interface ApprovementsRenewalDao {
	void insertApprovementsRenewal(ApprovementsRenewal approvementsRenewal, Callback before) throws Exception;

	List<ApprovementsRenewal> getAllApprovementsRenewal() throws Exception;

	ApprovementsRenewal getApprovementsRenewalById(String id) throws Exception;
	
	ApprovementsRenewal checkParticipantPresence(String idDtlModuleRgs, String idUser) throws Exception;
}
