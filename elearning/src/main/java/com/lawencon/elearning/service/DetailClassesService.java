package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.helper.ClassesHelper;
import com.lawencon.elearning.model.DetailClasses;

public interface DetailClassesService {
	void insertDetailClass(ClassesHelper clazzHelper) throws Exception;
	
	List<DetailClasses> getAllDetailClass() throws Exception;
}
