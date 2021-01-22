package com.lawencon.elearning.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.AssignmentTypes;
import com.lawencon.util.Callback;

@Repository
public class AssignmentTypesDaoImpl extends ElearningBaseDaoImpl<AssignmentTypes> implements AssignmentTypesDao{
	@Override
	public void insertAssignmentType(AssignmentTypes assignmentType, Callback before) throws Exception {
		save(assignmentType, before, null, true, true);
	}
	
	@Override
	public List<AssignmentTypes> getAllAssignmentTypes() throws Exception {
		return getAll();
	}
	
	public AssignmentTypes getAssignmentTypesById(String id) throws Exception {
		return getById(id);
	}
	
	@Override
	public void updateAssignmentType(AssignmentTypes assignmentType, Callback before) throws Exception {
		save(assignmentType, before, null, true, true);
	}
	
	@Override
	public void deleteAssignmentTypeById(String id) throws Exception {
		deleteById(id);
	}
	
	@Override
	public AssignmentTypes getAssignmentTypeByCode(String code) throws Exception {
		AssignmentTypes assignmentTypes = createQuery("FROM AssignmentTypes WHERE code = ?1", AssignmentTypes.class)
				.setParameter(1, code).getSingleResult();
		return assignmentTypes;
	}
}
