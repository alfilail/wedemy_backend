package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.model.ApprovementsRenewal;

public interface ApprovementsRenewalService {
	void insertApprovementsRenewal(ApprovementsRenewal approvementsRenewal) throws Exception;

	List<ApprovementsRenewal> getAllApprovementsRenewal() throws Exception;

	ApprovementsRenewal getApprovementsRenewalById(String id) throws Exception;

	ApprovementsRenewal checkParticipantPresence(String idDtlModuleRgs, String idUser) throws Exception;

}
