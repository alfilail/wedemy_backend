package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.DetailClasses;
import com.lawencon.util.Callback;

public interface DetailClassesDao {
	
	List<DetailClasses> getAllDetailClass() throws Exception;
	
	void insertDetailClass(DetailClasses detailClass, Callback before) throws Exception;
	
}
