package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.DetailClasses;
import com.lawencon.util.Callback;

public interface DetailClassesDao {

	List<DetailClasses> getAllDetailClass() throws Exception;

	void insertDetailClass(DetailClasses detailClass, Callback before) throws Exception;

	DetailClasses getDetailClassById(String id) throws Exception;

	DetailClasses getDetailClassByCode(String code) throws Exception;

	List<DetailClasses> getTutorClasses(String idTutor) throws Exception;
	
	List<DetailClasses> getPopularClasses() throws Exception;
	
	void updateViews(String id) throws Exception;
	
	void deleteClassById(String id, String idUser) throws Exception;
	
	void updateDetailClass(DetailClasses dtlClass, Callback before) throws Exception;
	
	List<DetailClasses> getAllDetailClassByIdClass(String idClass) throws Exception;

}
