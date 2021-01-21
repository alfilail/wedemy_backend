package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.model.LearningMaterials;

/**
 * @author Nur Alfilail
 */

public interface LearningMaterialsService {

	void insertLearningMaterial(LearningMaterials learningMaterial) throws Exception;

	List<LearningMaterials> getAllLearningMaterials() throws Exception;

	LearningMaterials getLearningMaterialById(String id) throws Exception;

	void updateLearningMaterial(LearningMaterials learningMaterial) throws Exception;

	void deleteLearningMaterialById(String id) throws Exception;

	LearningMaterials getLearningMaterialByCode(String code) throws Exception;
}
