package com.lawencon.elearning.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.LearningMaterialTypes;
import com.lawencon.util.Callback;

@Repository
public class AssignmentTypesDaoImpl extends ElearningBaseDaoImpl<LearningMaterialTypes> implements AssignmentTypesDao{
	@Override
	public void insertAssignmentType(LearningMaterialTypes assignmentType, Callback before) throws Exception {
		save(assignmentType, before, null, true, true);
	}
	
	@Override
	public List<LearningMaterialTypes> getAllAssignmentTypes() throws Exception {
		return getAll();
	}
	
	public LearningMaterialTypes getAssignmentTypesById(String id) throws Exception {
		return getById(id);
	}
	
	@Override
	public void updateAssignmentType(LearningMaterialTypes assignmentType, Callback before) throws Exception {
		save(assignmentType, before, null, true, true);
	}
	
	@Override
	public void deleteAssignmentTypeById(String id) throws Exception {
		deleteById(id);
	}
	
	@Override
	public LearningMaterialTypes getAssignmentTypeByCode(String code) throws Exception {
		LearningMaterialTypes assignmentTypes = createQuery("FROM AssignmentTypes WHERE code = ?1", LearningMaterialTypes.class)
				.setParameter(1, code).getSingleResult();
		return assignmentTypes;
	}
}
