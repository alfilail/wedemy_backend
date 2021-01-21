package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.AssignmentTypes;
import com.lawencon.util.Callback;

public interface AssignmentTypesDao {
	void insertAssignmentType(AssignmentTypes assignmentType, Callback before) throws Exception;
	
	List<AssignmentTypes> getAllAssignmentTypes() throws Exception;
	
	AssignmentTypes getAssignmentTypesById(String id) throws Exception;
	
	void updateAssignmentType(AssignmentTypes assignmentType, Callback before) throws Exception;
	
	void deleteAssignmentTypeById(String id) throws Exception;
	
	void deleteAllAsignmentTypes (AssignmentTypes assignmentType) throws Exception;
}