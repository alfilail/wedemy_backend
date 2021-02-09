package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.LearningMaterials;
import com.lawencon.util.Callback;

public interface LearningMaterialsDao {

	void insert(LearningMaterials learningMaterial, Callback before) throws Exception;

	List<LearningMaterials> getAllLearningMaterial() throws Exception;

	LearningMaterials getLearningMaterialById(String id) throws Exception;

	void update(LearningMaterials learningMaterial, Callback before) throws Exception;

	void deleteById(String id) throws Exception;

	LearningMaterials getByCode(String code) throws Exception;
	
	void softDeleteById(String id, String idUser) throws Exception;
}
