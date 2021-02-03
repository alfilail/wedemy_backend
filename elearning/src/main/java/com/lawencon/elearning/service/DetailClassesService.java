package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.model.DetailClasses;

public interface DetailClassesService {
	void insertDetailClass(DetailClasses detailClass) throws Exception;

	List<DetailClasses> getAllDetailClass() throws Exception;

	DetailClasses getDetailClassById(String id) throws Exception;

	DetailClasses getDetailClassByCode(String code) throws Exception;

	List<DetailClasses> getTutorClasses(String idTutor) throws Exception;
	
	List<DetailClasses> getPopularClasses() throws Exception;
}
