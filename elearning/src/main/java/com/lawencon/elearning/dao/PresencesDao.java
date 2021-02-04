package com.lawencon.elearning.dao;

import java.time.LocalDate;
import java.util.List;

import com.lawencon.elearning.model.Presences;
import com.lawencon.util.Callback;

public interface PresencesDao {

	void insertPresence(Presences presence, Callback before) throws Exception;

	List<Presences> getAllPresences() throws Exception;

	Presences getPresenceById(String id) throws Exception;

	void updatePresence(Presences presence, Callback before) throws Exception;

	void deletePresenceById(String id) throws Exception;

	Presences getPresenceByCode(String code) throws Exception;

	Presences doesTutorPresent(String idDtlModuleRgs) throws Exception;

	List<?> getPresenceReport(String idClass, LocalDate scheduleDateStart, LocalDate scheduleDateEnd) throws Exception;

}
