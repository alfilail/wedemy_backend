package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.LearningMaterialTypes;
import com.lawencon.util.Callback;

/**
 * @author Nur Alfilail
 */

public interface LearningMaterialTypesDao {

	void insert(LearningMaterialTypes lmType, Callback before) throws Exception;

	List<LearningMaterialTypes> getAllLearningMaterialType() throws Exception;

	LearningMaterialTypes getLearningMaterialTypeById(String id) throws Exception;

	void update(LearningMaterialTypes lmType, Callback before) throws Exception;

	void deleteById(String id) throws Exception;

	LearningMaterialTypes getByCode(String code) throws Exception;

	void softDeleteById(String id, String idUser) throws Exception;

	List<?> validateDelete(String id) throws Exception;
	
	
}
