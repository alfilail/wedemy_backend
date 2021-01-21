package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.model.AssignmentTypes;

public interface AssignmentTypesService {
	void insertAssignmentType(AssignmentTypes assignmentType) throws Exception;
	
	List<AssignmentTypes> getAllAssignmentTypes() throws Exception;
	
	AssignmentTypes getAssignmentTypeById (String id) throws Exception;
	
	void deleteAssignmentTypeById (String id) throws Exception;
	
	void updateAssignmentType(AssignmentTypes assignmentType) throws Exception;
}
