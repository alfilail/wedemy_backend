package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.LearningMaterialTypes;
import com.lawencon.util.Callback;

/**
 * @author Nur Alfilail
 */

public class LearningMaterialTypesDaoImpl extends ElearningBaseDaoImpl<LearningMaterialTypes>
		implements LearningMaterialTypesDao {

	@Override
	public void insertLearningMaterialType(LearningMaterialTypes lmType, Callback before) throws Exception {
		save(lmType, before, null, true, true);
	}

	@Override
	public List<LearningMaterialTypes> getAllLearningMaterialTypes() throws Exception {
		return getAll();
	}

	@Override
	public LearningMaterialTypes getLearningMaterialTypeById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public void updateLearningMaterialType(LearningMaterialTypes lmType, Callback before) throws Exception {
		save(lmType, before, null, true, true);
	}

	@Override
	public void deleteLearningMaterialTypeById(String id) throws Exception {
		deleteById(id);
	}

	@Override
	public LearningMaterialTypes getLearningMaterialTypeByCode(String code) throws Exception {
		LearningMaterialTypes lmType = createQuery("FROM LearningMaterialTypes WHERE code = ?1",
				LearningMaterialTypes.class).setParameter(1, code).getSingleResult();
		return lmType;
	}

}
