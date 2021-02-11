package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.DetailClasses;
import com.lawencon.util.Callback;

public interface DetailClassesDao {

	List<DetailClasses> getAllDetailClass() throws Exception;
	
	List<DetailClasses> getAllInactive() throws Exception;

	void insert(DetailClasses detailClass, Callback before) throws Exception;

	DetailClasses getDetailClassById(String id) throws Exception;

	DetailClasses getByCode(String code) throws Exception;

	List<DetailClasses> getTutorClasses(String idTutor) throws Exception;
	
	List<DetailClasses> getPopularClasses() throws Exception;
	
	void updateViews(String id) throws Exception;
	
	void deleteDtlClassById(String id, String idUser) throws Exception;
	
	void update(DetailClasses dtlClass, Callback before) throws Exception;
	
	List<DetailClasses> getAllByIdClass(String idClass) throws Exception;
	
	DetailClasses getByIdClass(String idClass) throws Exception;

}
