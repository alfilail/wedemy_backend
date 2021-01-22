package com.lawencon.elearning.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.LearningMaterials;
import com.lawencon.util.Callback;

@Repository
public class LearningMaterialsDaoImpl extends ElearningBaseDaoImpl<LearningMaterials> implements LearningMaterialsDao {

	@Override
	public void insertLearningMaterial(LearningMaterials learningMaterial, Callback before) throws Exception {
		save(learningMaterial, before, null, true, true);
	}

	@Override
	public List<LearningMaterials> getAllLearningMaterials() throws Exception {
		return getAll();
	}

	@Override
	public LearningMaterials getLearningMaterialById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public void updateLearningMaterial(LearningMaterials learningMaterial, Callback before) throws Exception {
		save(learningMaterial, before, null, true, true);
	}

	@Override
	public void deleteLearningMaterialById(String id) throws Exception {
		deleteById(id);
	}

	@Override
	public LearningMaterials getLearningMaterialByCode(String code) throws Exception {
		LearningMaterials learningMaterial = createQuery("FROM LearningMaterials WHERE code =?1",
				LearningMaterials.class).setParameter(1, code).getSingleResult();
		return learningMaterial;
	}

}
