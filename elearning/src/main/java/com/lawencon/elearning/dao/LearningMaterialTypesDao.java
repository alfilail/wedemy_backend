package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.LearningMaterialTypes;
import com.lawencon.util.Callback;

/**
 * @author Nur Alfilail
 */

public interface LearningMaterialTypesDao {

	void insertLearningMaterialType(LearningMaterialTypes lmType, Callback before) throws Exception;

	List<LearningMaterialTypes> getAllLearningMaterialTypes() throws Exception;

	LearningMaterialTypes getLearningMaterialTypeById(String id) throws Exception;

	void updateLearningMaterialType(LearningMaterialTypes lmType, Callback before) throws Exception;

	void deleteLearningMaterialTypeById(String id) throws Exception;

	LearningMaterialTypes getLearningMaterialTypeByCode(String code) throws Exception;

	void softDeleteLearningMaterialTypeById(String id, String idUser) throws Exception;

	List<?> validateDeleteLearningMaterialType(String id) throws Exception;
	
	
}
