package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.LearningMaterialsDao;
import com.lawencon.elearning.model.DetailModuleRegistrations;
import com.lawencon.elearning.model.LearningMaterialTypes;
import com.lawencon.elearning.model.LearningMaterials;

/**
 * @author Nur Alfilail
 */

@Service
public class LearningMaterialsServiceImpl extends BaseServiceImpl implements LearningMaterialsService {

	@Autowired
	private LearningMaterialsDao learningMaterialsDao;

	@Autowired
	private DetailModuleRegistrationsService dtlModRegistService;

	@Autowired
	private LearningMaterialTypesService learningMaterialTypesService;

	@Override
	public void insertLearningMaterial(DetailModuleRegistrations dtlModuleRgs, MultipartFile file) throws Exception {
		try {
			begin();
			dtlModuleRgs.getIdLearningMaterial().setFile(file.getBytes());
			dtlModuleRgs.getIdLearningMaterial().setFileType(file.getContentType());
			learningMaterialsDao.insertLearningMaterial(dtlModuleRgs.getIdLearningMaterial(),
					() -> validateInsert(dtlModuleRgs.getIdLearningMaterial()));
			insertDetailModulRegistration(dtlModuleRgs);
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

	private void insertDetailModulRegistration(DetailModuleRegistrations dtlModuleRgs) throws Exception {
//		ModuleRegistrations modRegist = moduleRegistrationsService.getByIdDetailClassAndIdModuleRegistration(
//				helper.getModuleRegistration().getIdDetailClass().getId(),
//				helper.getModuleRegistration().getIdModule().getId());
//		DetailModuleRegistrations dtlModRegist = helper.getDtlModuleRegistration();
		dtlModuleRgs.setCreatedBy(dtlModuleRgs.getIdLearningMaterial().getCreatedBy());
		dtlModuleRgs.setIdLearningMaterial(dtlModuleRgs.getIdLearningMaterial());
		dtlModRegistService.insertDetailModuleRegistration(dtlModuleRgs);
	}

	private void validateInsert(LearningMaterials learningMaterial) throws Exception {
		if (learningMaterial.getCode() == null || learningMaterial.getCode().trim().equals("")) {
			throw new Exception("Kode bahan ajar tidak boleh kosong!");
		} else {
			LearningMaterials learningMaterials = getLearningMaterialByCode(learningMaterial.getCode());
			if (learningMaterials != null) {
				throw new Exception("Kode bahan ajar tidak boleh sama!");
			} else {
				if (learningMaterial.getIdLearningMaterialType() == null) {
					LearningMaterialTypes materialType = learningMaterialTypesService
							.getLearningMaterialTypeById(learningMaterial.getIdLearningMaterialType().getId());
					if (materialType == null) {
						throw new Exception("Id tipe bahan ajar tidak ada!");
					} else {
						String[] type = learningMaterial.getFileType().split("/");
						String ext = type[1];
						if (ext != null) {
							if (ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("png")
									|| ext.equalsIgnoreCase("jpeg")) {
							} else {
								throw new Exception("File harus gambar!");
							}
						} else if (learningMaterial.getLearningMaterialName() == null
								|| learningMaterial.getLearningMaterialName().trim().equals("")) {
							throw new Exception("Nama bahan ajar tidak boleh kosong!");
						} else if (learningMaterial.getDescription() == null
								|| learningMaterial.getDescription().trim().equals("")) {
							throw new Exception("Deskripsi bahan ajar tidak boleh kosong!");
						}
					}
				}
			}
		}
	}

	private void validateUpdate(LearningMaterials learningMaterial) throws Exception {
		if (learningMaterial.getId() == null || learningMaterial.getId().trim().equals("")) {
			throw new Exception("Id kelas tidak boleh kosong!");
		} else {
			LearningMaterials lm = getLearningMaterialById(learningMaterial.getId());
			if (learningMaterial.getVersion() == null) {
				throw new Exception("Kelas version tidak boleh kosong!");
			} else {
				if (learningMaterial.getVersion() != lm.getVersion()) {
					throw new Exception("Kelas version tidak sama!");
				} else {
					if (learningMaterial.getCode() == null || learningMaterial.getCode().trim().equals("")) {
						throw new Exception("Kode bahan ajar tidak boleh kosong!");
					} else {
						LearningMaterials learningMaterials = getLearningMaterialByCode(learningMaterial.getCode());
						if (learningMaterials != null) {
							throw new Exception("Kode bahan ajar tidak boleh sama!");
						} else {
							if (learningMaterial.getIdLearningMaterialType() == null) {
								LearningMaterialTypes materialType = learningMaterialTypesService
										.getLearningMaterialTypeById(
												learningMaterial.getIdLearningMaterialType().getId());
								if (materialType == null) {
									throw new Exception("Id tipe bahan ajar tidak ada!");
								} else {
									String[] type = learningMaterial.getFileType().split("/");
									String ext = type[1];
									if (ext != null) {
										if (ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("png")
												|| ext.equalsIgnoreCase("jpeg")) {
										} else {
											throw new Exception("File harus gambar!");
										}
									} else if (learningMaterial.getLearningMaterialName() == null
											|| learningMaterial.getLearningMaterialName().trim().equals("")) {
										throw new Exception("Nama bahan ajar tidak boleh kosong!");
									} else if (learningMaterial.getDescription() == null
											|| learningMaterial.getDescription().trim().equals("")) {
										throw new Exception("Deskripsi bahan ajar tidak boleh kosong!");
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
