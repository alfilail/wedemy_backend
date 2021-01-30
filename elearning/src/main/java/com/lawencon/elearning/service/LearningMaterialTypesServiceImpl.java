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

	private void validateInsert(LearningMaterialTypes learningMaterialTypes) throws Exception {
		if (learningMaterialTypes.getCode() == null || learningMaterialTypes.getCode().trim().equals("")) {
			throw new Exception("Kode tipe bahan ajar tidak boleh kosong!");
		} else {
			LearningMaterialTypes learningMaterialType = getLearningMaterialTypeByCode(learningMaterialTypes.getCode());
			if (learningMaterialType != null) {
				throw new Exception("Kode tipe bahan ajar tidak boleh sama!");
			} else {
				if (learningMaterialTypes.getLearningMaterialTypeName() == null
						|| learningMaterialTypes.getLearningMaterialTypeName().trim().equals("")) {
					throw new Exception("Nama tipe bahan ajar tidak boleh kosong!");
				}
			}
		}
	}

	private void validateUpdate(LearningMaterialTypes learningMaterialTypes) throws Exception {
		if (learningMaterialTypes.getId() == null || learningMaterialTypes.getId().trim().equals("")) {
			throw new Exception("Id tipe bahan ajar tidak boleh kosong!");
		} else {
			LearningMaterialTypes learningType = getLearningMaterialTypeById(learningMaterialTypes.getId());
			if (learningType == null) {
				throw new Exception("Id tipe bahan ajar tidak ada!");
			} else {
				if (learningType.getVersion() == null) {
					throw new Exception("Tipe bahan ajar version tidak boleh kosong!");
				} else {
					if (learningMaterialTypes.getVersion() != learningType.getVersion()) {
						throw new Exception("Tipe bahan ajar version tidak sama!");
					} else {
						if (learningMaterialTypes.getCode() == null
								|| learningMaterialTypes.getCode().trim().equals("")) {
							throw new Exception("Kode tipe bahan ajar tidak boleh kosong!");
						} else {
							LearningMaterialTypes learningMaterialType = getLearningMaterialTypeByCode(
									learningMaterialTypes.getCode());
							if (learningMaterialType != null) {
								throw new Exception("Kode tipe bahan tidak boleh sama!");
							} else {
								if (learningMaterialTypes.getLearningMaterialTypeName() == null
										|| learningMaterialTypes.getLearningMaterialTypeName().trim().equals("")) {
									throw new Exception("Name tipe bahan ajar tidak boleh kosong!");
								}
							}
						}
					}
				}
			}
		}
	}
}