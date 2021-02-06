package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.model.ApprovementsRenewal;

public interface ApprovementsRenewalService {
	void insertApprovementsRenewal(ApprovementsRenewal approvementsRenewal) throws Exception;

	void participantApprovementsRenewal(ApprovementsRenewal approvementsRenewal) throws Exception;

	List<ApprovementsRenewal> getAllApprovementsRenewal() throws Exception;
	
	List<ApprovementsRenewal> getListParticipantsPresence(String idDtlClass, String idDtlModuleRgs) throws Exception;

	ApprovementsRenewal getApprovementsRenewalById(String id) throws Exception;

	ApprovementsRenewal checkParticipantPresence(String idDtlModuleRgs, String idUser) throws Exception;

}
