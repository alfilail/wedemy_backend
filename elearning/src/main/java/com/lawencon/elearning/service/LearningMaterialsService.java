package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lawencon.elearning.helper.LearningMaterialHelper;
import com.lawencon.elearning.model.LearningMaterials;

/**
 * @author Nur Alfilail
 */

public interface LearningMaterialsService {

	void insertLearningMaterial(LearningMaterialHelper helper, MultipartFile file) throws Exception;

	List<LearningMaterials> getAllLearningMaterials() throws Exception;

	LearningMaterials getLearningMaterialById(String id) throws Exception;

	void updateLearningMaterial(LearningMaterials learningMaterial, MultipartFile file) throws Exception;

	void deleteLearningMaterialById(String id) throws Exception;

	LearningMaterials getLearningMaterialByCode(String code) throws Exception;
}
