package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.AssignmentTypesDao;
import com.lawencon.elearning.model.LearningMaterialTypes;

@Service
public class LearningMaterialTypesServiceImpl extends BaseServiceImpl implements LearningMaterialTypesService {
	@Autowired
	private AssignmentTypesDao assignmentTypesDao;

	@Override
	public void insertLearningMaterialType(LearningMaterialTypes assignmentType) throws Exception {
		assignmentTypesDao.insertAssignmentType(assignmentType, () -> validateInsert(assignmentType));
	}

	@Override
	public List<LearningMaterialTypes> getAllLearningMaterialTypes() throws Exception {
		return assignmentTypesDao.getAllAssignmentTypes();
	}

	@Override
	public LearningMaterialTypes getLearningMaterialTypeById(String id) throws Exception {
		return assignmentTypesDao.getAssignmentTypesById(id);
	}

	@Override
	public void deleteLearningMaterialTypeById(String id) throws Exception {
		assignmentTypesDao.deleteAssignmentTypeById(id);
	}

	@Override
	public void updateLearningMaterialType(LearningMaterialTypes assignmentType) throws Exception {
		assignmentTypesDao.updateAssignmentType(assignmentType, () -> validateUpdate(assignmentType));
	}

	@Override
	public LearningMaterialTypes getLearningMaterialTypeByCode(String code) throws Exception {
		return assignmentTypesDao.getAssignmentTypeByCode(code);
	}

	private void validateInsert(LearningMaterialTypes assignmentType) throws Exception {
		if (assignmentType.getCode() == null) {
			throw new Exception("Invalid Input Assignment Type Code");
		}
	}

	private void validateUpdate(LearningMaterialTypes assignmentType) throws Exception {
		if (assignmentType.getCode() == null) {
			throw new Exception("Invalid Input Assignment Type Code");
		}
	}
}