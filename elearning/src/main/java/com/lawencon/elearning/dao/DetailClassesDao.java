package com.lawencon.elearning.dao;

import com.lawencon.elearning.model.DetailClasses;
import com.lawencon.util.Callback;

public interface DetailClassesDao {
	void insertDetailClass(DetailClasses detailClass, Callback before) throws Exception;
}
