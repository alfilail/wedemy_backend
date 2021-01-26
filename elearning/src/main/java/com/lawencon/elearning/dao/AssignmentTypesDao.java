package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.LearningMaterialTypes;
import com.lawencon.util.Callback;

public interface AssignmentTypesDao {
	void insertAssignmentType(LearningMaterialTypes assignmentType, Callback before) throws Exception;
	
	List<LearningMaterialTypes> getAllAssignmentTypes() throws Exception;
	
	LearningMaterialTypes getAssignmentTypesById(String id) throws Exception;
	
	void updateAssignmentType(LearningMaterialTypes assignmentType, Callback before) throws Exception;
	
	void deleteAssignmentTypeById(String id) throws Exception;
	
	LearningMaterialTypes getAssignmentTypeByCode(String code) throws Exception;
}