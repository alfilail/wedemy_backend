package com.lawencon.elearning.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.AssignmentTypesDao;
import com.lawencon.elearning.model.AssignmentTypes;

@Service
public class AssignmentTypesServiceImpl extends BaseServiceImpl implements AssignmentTypesService {
	@Autowired
	private AssignmentTypesDao assignmentTypesDao;

	@Override
	public void insertAssignmentType(AssignmentTypes assignmentType) throws Exception {
		assignmentType.setCreatedAt(LocalDateTime.now());
		assignmentTypesDao.insertAssignmentType(assignmentType, 
				() -> validateInsert(assignmentType));
	}

	@Override
	public List<AssignmentTypes> getAllAssignmentTypes() throws Exception {
		return assignmentTypesDao.getAllAssignmentTypes();
	}

	@Override
	public AssignmentTypes getAssignmentTypeById(String id) throws Exception {
		return assignmentTypesDao.getAssignmentTypesById(id);
	}

	@Override
	public void deleteAssignmentTypeById(String id) throws Exception {
		assignmentTypesDao.deleteAssignmentTypeById(id);
	}

	@Override
	public void updateAssignmentType(AssignmentTypes assignmentType) throws Exception {
		assignmentType.setUpdatedAt(LocalDateTime.now());
		assignmentTypesDao.updateAssignmentType(assignmentType, 
				()-> validateUpdate(assignmentType));
	}

	@Override
	public AssignmentTypes getAssignmentTypeByCode(String code) throws Exception {
		return assignmentTypesDao.getAssignmentTypeByCode(code);
	}

	private void validateInsert(AssignmentTypes assignmentType) throws Exception {
		if (assignmentType.getCode() == null) {
			throw new Exception("Invalid Input Assignment Type Code");
		}
	}
	
	private void validateUpdate(AssignmentTypes assignmentType) throws Exception {
		if (assignmentType.getCode() == null) {
			throw new Exception("Invalid Input Assignment Type Code");
		}
	}
}