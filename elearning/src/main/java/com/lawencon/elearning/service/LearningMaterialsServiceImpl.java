package com.lawencon.elearning.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.LearningMaterialsDao;
import com.lawencon.elearning.model.DetailModuleRegistrations;
import com.lawencon.elearning.model.Files;
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

	@Autowired
	private FilesService filesService;

	@Autowired
	ForumsService forumsService;

	@Override
	public void insert(DetailModuleRegistrations dtlModuleRgs, MultipartFile fileInput) throws Exception {
		try {
			begin();
			Files file = new Files();
			file.setCreatedBy(dtlModuleRgs.getIdLearningMaterial().getCreatedBy());
			file.setFile(fileInput.getBytes());
			file.setType(fileInput.getContentType());
			filesService.insert(file);
			dtlModuleRgs.getIdLearningMaterial().setIdFile(file);
			learningMaterialsDao.insert(dtlModuleRgs.getIdLearningMaterial(),
					() -> validateInsert(dtlModuleRgs.getIdLearningMaterial()));
			insertDetailModulRegistration(dtlModuleRgs);
			commit();
		} catch (Exception e) {
			rollback();
			throw new Exception(e);
		}
	}

	@Override
	public List<LearningMaterials> getAll() throws Exception {
		return learningMaterialsDao.getAllLearningMaterial();
	}

	@Override
	public LearningMaterials getById(String id) throws Exception {
		return learningMaterialsDao.getLearningMaterialById(id);
	}

	@Override
	public void update(DetailModuleRegistrations dtlModuleRgs, MultipartFile fileInput) throws Exception {
		try {
			begin();
			Files file = filesService.getById(dtlModuleRgs.getIdLearningMaterial().getIdFile().getId());
			if (fileInput != null && !fileInput.isEmpty()) {
				file.setFile(fileInput.getBytes());
				file.setType(fileInput.getContentType());
				file.setCreatedAt(file.getCreatedAt());
				file.setCreatedBy(file.getCreatedBy());
				filesService.update(file);
			}
			LearningMaterials material = learningMaterialsDao
					.getLearningMaterialById(dtlModuleRgs.getIdLearningMaterial().getId());
			dtlModuleRgs.getIdLearningMaterial().setCreatedAt(material.getCreatedAt());
			dtlModuleRgs.getIdLearningMaterial().setCreatedBy(material.getCreatedBy());
			learningMaterialsDao.update(dtlModuleRgs.getIdLearningMaterial(),
					() -> validateUpdate(dtlModuleRgs.getIdLearningMaterial()));
			dtlModRegistService.update(dtlModuleRgs);
			commit();
		} catch (Exception e) {
			rollback();
			throw new Exception(e);
		}
	}

	@Override
	public void deleteById(String id, String idUser) throws Exception {
		try {
			begin();
			if(checkDelete(id) == false) {
				learningMaterialsDao.softDeleteById(id, idUser);
			}
			else {
				validateDelete();
			}
			commit();
		} catch (Exception e) {
			rollback();
			throw new Exception(e);
		}
	}

	@Override
	public LearningMaterials getByCode(String code) throws Exception {
		return learningMaterialsDao.getByCode(code);
	}

	private void insertDetailModulRegistration(DetailModuleRegistrations dtlModuleRgs) throws Exception {
		dtlModuleRgs.setCreatedBy(dtlModuleRgs.getIdLearningMaterial().getCreatedBy());
		dtlModuleRgs.setIdLearningMaterial(dtlModuleRgs.getIdLearningMaterial());
		dtlModRegistService.insertDetailModuleRegistration(dtlModuleRgs);
	}

	private void validateInsert(LearningMaterials learningMaterial) throws Exception {
		if (learningMaterial.getCode() == null || learningMaterial.getCode().trim().equals("")) {
			throw new Exception("Kode bahan ajar tidak boleh kosong!");
		} else {
			LearningMaterials learningMaterials = getByCode(learningMaterial.getCode());
			if (learningMaterials != null) {
				throw new Exception("Kode bahan ajar tidak boleh sama!");
			} else {
				if (learningMaterial.getIdLearningMaterialType() == null) {
					LearningMaterialTypes materialType = learningMaterialTypesService
							.getById(learningMaterial.getIdLearningMaterialType().getId());
					if (materialType == null) {
						throw new Exception("Id tipe bahan ajar tidak ada!");
					} else {
						String[] type = learningMaterial.getIdFile().getType().split("/");
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
			LearningMaterials lm = getById(learningMaterial.getId());
			if (learningMaterial.getVersion() == null) {
				throw new Exception("Kelas version tidak boleh kosong!");
			} else {
				if (learningMaterial.getVersion() != lm.getVersion()) {
					throw new Exception("Kelas version tidak sama!");
				} else {
					if (learningMaterial.getCode() == null || learningMaterial.getCode().trim().equals("")) {
						throw new Exception("Kode bahan ajar tidak boleh kosong!");
					} else {
						if (!learningMaterial.getCode().equalsIgnoreCase(lm.getCode())) {
							LearningMaterials learningMaterials = getByCode(learningMaterial.getCode());
							if (learningMaterials != null) {
								throw new Exception("Kode bahan ajar tidak boleh sama!");
							}
						} else {
							if (learningMaterial.getIdLearningMaterialType() == null) {
								LearningMaterialTypes materialType = learningMaterialTypesService
										.getById(learningMaterial.getIdLearningMaterialType().getId());
								if (materialType == null) {
									throw new Exception("Id tipe bahan ajar tidak ada!");
								} else {
									String[] type = learningMaterial.getIdFile().getType().split("/");
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
	
	private boolean checkDelete(String id) throws Exception {
		List<?> listObj = learningMaterialsDao.validateDelete(id);
		List<?> list =  listObj.stream().filter(val -> val != null)
				.collect(Collectors.toList());
		return list.size() > 0 ? true : false;
	}
	
	private void validateDelete() throws Exception{
		throw new Exception("Bahan ajar tidak boleh dihapus!");
	}
}
