package com.lawencon.elearning.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.LearningMaterialsDao;
import com.lawencon.elearning.model.LearningMaterials;

/**
 * @author Nur Alfilail
 */

@Service
public class LearningMaterialsServiceImpl extends BaseServiceImpl implements LearningMaterialsService {

	@Autowired
	private LearningMaterialsDao learningMaterialsDao;

	@Override
	public void insertLearningMaterial(LearningMaterials learningMaterial) throws Exception {
		learningMaterial.setCreatedAt(LocalDateTime.now());
		learningMaterialsDao.insertLearningMaterial(learningMaterial, () -> validateInsert(learningMaterial));
	}

	@Override
	public List<LearningMaterials> getAllLearningMaterials() throws Exception {
		return learningMaterialsDao.getAllLearningMaterials();
	}

	@Override
	public LearningMaterials getLearningMaterialById(String id) throws Exception {
		return learningMaterialsDao.getLearningMaterialById(id);
	}

	@Override
	public void updateLearningMaterial(LearningMaterials learningMaterial) throws Exception {
		learningMaterial.setUpdatedAt(LocalDateTime.now());
		learningMaterialsDao.updateLearningMaterial(learningMaterial, () -> validateUpdate(learningMaterial));
	}

	@Override
	public void deleteLearningMaterialById(String id) throws Exception {
		learningMaterialsDao.deleteLearningMaterialById(id);
	}

	@Override
	public LearningMaterials getLearningMaterialByCode(String code) throws Exception {
		return learningMaterialsDao.getLearningMaterialByCode(code);
	}

	private void validateInsert(LearningMaterials learningMaterial) throws Exception {

	}

	private void validateUpdate(LearningMaterials learningMaterial) throws Exception {

	}

}
