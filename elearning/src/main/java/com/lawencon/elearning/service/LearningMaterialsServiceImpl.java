package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.LearningMaterialsDao;
import com.lawencon.elearning.helper.LearningMaterialsHelper;
import com.lawencon.elearning.model.DetailModuleRegistrations;
import com.lawencon.elearning.model.LearningMaterials;
import com.lawencon.elearning.model.ModuleRegistrations;

/**
 * @author Nur Alfilail
 */

@Service
public class LearningMaterialsServiceImpl extends BaseServiceImpl implements LearningMaterialsService {

	@Autowired
	private LearningMaterialsDao learningMaterialsDao;

	@Autowired
	private ModuleRegistrationsService moduleRegistrationsService;

	@Autowired
	private DetailModuleRegistrationsService dtlModRegistService;

	@Override
	public void insertLearningMaterial(LearningMaterialsHelper helper, MultipartFile file) throws Exception {
		try {
			begin();
			helper.getLearningMaterial().setFile(file.getBytes());
			helper.getLearningMaterial().setFileType(file.getContentType());
			learningMaterialsDao.insertLearningMaterial(helper.getLearningMaterial(),
					() -> validateInsert(helper.getLearningMaterial()));
			insertDetailModulRegistration(helper);
			commit();
		} catch (Exception e) {
			rollback();
			throw new Exception(e);
		}
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
	public void updateLearningMaterial(LearningMaterials learningMaterial, MultipartFile file) throws Exception {
		learningMaterial.setFile(file.getBytes());
		learningMaterial.setFileType(file.getContentType());
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

	private void insertDetailModulRegistration(LearningMaterialsHelper helper) throws Exception {
		ModuleRegistrations modRegist = moduleRegistrationsService.getByIdDetailClassAndIdModuleRegistration(
				helper.getModuleRegistration().getIdDetailClass().getId(),
				helper.getModuleRegistration().getIdModule().getId());
		DetailModuleRegistrations dtlModRegist = helper.getDtlModuleRegistration();
		dtlModRegist.setCreatedBy(helper.getLearningMaterial().getCreatedBy());
		dtlModRegist.setIdLearningMaterial(helper.getLearningMaterial());
		dtlModRegist.setIdModuleRegistration(modRegist);
		dtlModRegistService.insertDetailModuleRegistration(dtlModRegist);
	}

	private void validateInsert(LearningMaterials learningMaterial) throws Exception {

	}

	private void validateUpdate(LearningMaterials learningMaterial) throws Exception {

	}

}
