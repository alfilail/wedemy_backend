package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.ApprovementsRenewal;
import com.lawencon.util.Callback;

public interface ApprovementsRenewalDao {
	void insertApprovementsRenewal(ApprovementsRenewal approvementsRenewal, Callback before) throws Exception;
	
	void participantApprovementsRenewal(ApprovementsRenewal approvementsRenewal, Callback before) throws Exception;

	List<ApprovementsRenewal> getAllApprovementsRenewal() throws Exception;

	List<ApprovementsRenewal> getListParticipantsPresence(String idDtlClass, String idDtlModuleRgs) throws Exception;

	ApprovementsRenewal getApprovementsRenewalById(String id) throws Exception;

	ApprovementsRenewal checkParticipantPresence(String idDtlModuleRgs, String idUser) throws Exception;
}
