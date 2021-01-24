package com.lawencon.elearning.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.LearningMaterialsDao;
import com.lawencon.elearning.helper.LearningMaterialHelper;
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
	public void insertLearningMaterial(LearningMaterialHelper helper, 
			MultipartFile file) throws Exception {
		LearningMaterials learnMaterial = helper.getLearningMaterials();
		learnMaterial.setCreatedAt(LocalDateTime.now());
		learnMaterial.setFile(file.getBytes());
		learnMaterial.setFileType(file.getContentType());
		learningMaterialsDao.insertLearningMaterial(learnMaterial, () -> validateInsert(learnMaterial));
		System.out.println("Id learn material : " + learnMaterial.getId());
		ModuleRegistrations modRegist = moduleRegistrationsService
				.getByIdClassAndIdModuleRegistration(helper.getModuleRegistrations().getIdDetailClass().getId(), 
				helper.getModuleRegistrations().getIdModule().getId());
		System.out.println("ini ni" + modRegist.getId());
		DetailModuleRegistrations dtlModRegist = new DetailModuleRegistrations();
		dtlModRegist.setIdLearningMaterial(learnMaterial);
		dtlModRegist.setIdModuleRegistration(modRegist);
		dtlModRegist.setOrderNumber(1);
		dtlModRegistService.insertDetailModuleRegistration(dtlModRegist);
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
		learningMaterial.setUpdatedAt(LocalDateTime.now());
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

	private void validateInsert(LearningMaterials learningMaterial) throws Exception {

	}

	private void validateUpdate(LearningMaterials learningMaterial) throws Exception {

	}

}
