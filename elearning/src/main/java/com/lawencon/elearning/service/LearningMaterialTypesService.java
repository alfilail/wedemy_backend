package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.model.LearningMaterialTypes;

public interface LearningMaterialTypesService {
	void insertLearningMaterialType(LearningMaterialTypes lmType) throws Exception;

	List<LearningMaterialTypes> getAllLearningMaterialTypes() throws Exception;

	LearningMaterialTypes getLearningMaterialTypeById(String id) throws Exception;

	void deleteLearningMaterialTypeById(String id, String idUser) throws Exception;

	void updateLearningMaterialType(LearningMaterialTypes lmType) throws Exception;

	LearningMaterialTypes getLearningMaterialTypeByCode(String code) throws Exception;
}
