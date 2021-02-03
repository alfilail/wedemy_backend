package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.LearningMaterials;
import com.lawencon.util.Callback;

public interface LearningMaterialsDao {

	void insertLearningMaterial(LearningMaterials learningMaterial, Callback before) throws Exception;

	List<LearningMaterials> getAllLearningMaterials() throws Exception;

	LearningMaterials getLearningMaterialById(String id) throws Exception;

	void updateLearningMaterial(LearningMaterials learningMaterial, Callback before) throws Exception;

	void deleteLearningMaterialById(String id) throws Exception;

	LearningMaterials getLearningMaterialByCode(String code) throws Exception;
	
	void softDeleteLearningMaterialById(String id, String idUser) throws Exception;

	List<?> validateDeleteLearningMaterial(String id) throws Exception;
}
