package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.model.Presences;

/**
 @author Nur Alfilail
*/

public interface PresencesService {

	void insertPresence(Presences presence) throws Exception;

	List<Presences> getAllPresences() throws Exception;

	Presences getPresenceById(String id) throws Exception;

	void updatePresence(Presences presence) throws Exception;

	void deletePresenceById(String id) throws Exception;

	Presences getPresenceByCode(String code) throws Exception;
	
	Presences doesTutorPresent(String idDtlModuleRgs) throws Exception;
	
	Presences doesParticipantPresent(String idDtlModuleRgs, String idParticipant) throws Exception;
	
	List<?> getPresenceReport(String idDetailClass) throws Exception;
}
