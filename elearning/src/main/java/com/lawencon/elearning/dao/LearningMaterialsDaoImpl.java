package com.lawencon.elearning.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.LearningMaterials;
import com.lawencon.util.Callback;

@Repository
public class LearningMaterialsDaoImpl extends ElearningBaseDaoImpl<LearningMaterials> implements LearningMaterialsDao {

	@Override
	public void insert(LearningMaterials learningMaterial, Callback before) throws Exception {
		save(learningMaterial, before, null);
	}

	@Override
	public List<LearningMaterials> getAllLearningMaterial() throws Exception {
		return getAll();
	}

	@Override
	public LearningMaterials getLearningMaterialById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public void update(LearningMaterials learningMaterial, Callback before) throws Exception {
		save(learningMaterial, before, null);
	}

	@Override
	public void deleteById(String id) throws Exception {
		deleteById(id);
	}

	@Override
	public LearningMaterials getByCode(String code) throws Exception {
		List<LearningMaterials> learningMaterial = createQuery("FROM LearningMaterials WHERE code =?1",
				LearningMaterials.class).setParameter(1, code).getResultList();
		return resultCheck(learningMaterial);
	}

	@Override
	public void softDeleteById(String id, String idUser) throws Exception {
		updateNativeSQL("UPDATE t_m_learning_materials SET is_active = false", id, idUser);
	}

}
