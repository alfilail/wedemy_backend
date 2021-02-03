package com.lawencon.elearning.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
//import com.lawencon.elearning.dao.AssignmentTypesDao;
import com.lawencon.elearning.dao.LearningMaterialTypesDao;
import com.lawencon.elearning.model.LearningMaterialTypes;

@Service
public class LearningMaterialTypesServiceImpl extends BaseServiceImpl implements LearningMaterialTypesService {
	
	@Autowired
	private LearningMaterialTypesDao learnMaterialTypeDao;

	@Override
	public void insertLearningMaterialType(LearningMaterialTypes lmType) throws Exception {
		learnMaterialTypeDao.insertLearningMaterialType(lmType, () -> validateInsert(lmType));
	}

	@Override
	public List<LearningMaterialTypes> getAllLearningMaterialTypes() throws Exception {
		return learnMaterialTypeDao.getAllLearningMaterialTypes();
	}

	@Override
	public LearningMaterialTypes getLearningMaterialTypeById(String id) throws Exception {
		return learnMaterialTypeDao.getLearningMaterialTypeById(id);
	}

	@Override
	public void deleteLearningMaterialTypeById(String id, String idUser) throws Exception {
		try {
			begin();
			if(validateDelete(id)) {
				learnMaterialTypeDao.softDeleteLearningMaterialTypeById(id, idUser);
			} else {				
				learnMaterialTypeDao.deleteLearningMaterialTypeById(id);
			}
			commit();
		} catch(Exception e) {
			e.getMessage();
			rollback();
		}
	}

	@Override
	public void updateLearningMaterialType(LearningMaterialTypes lmType) throws Exception {
		learnMaterialTypeDao.updateLearningMaterialType(lmType, () -> validateUpdate(lmType));
	}

	@Override
	public LearningMaterialTypes getLearningMaterialTypeByCode(String code) throws Exception {
		return learnMaterialTypeDao.getLearningMaterialTypeByCode(code);
	}

	private void validateInsert(LearningMaterialTypes learningMaterialTypes) throws Exception {
		if (learningMaterialTypes.getCode() == null || learningMaterialTypes.getCode().trim().equals("")) {
			throw new Exception("Kode tipe bahan ajar tidak boleh kosong!");
		} else {
			LearningMaterialTypes learningMaterialType = getLearningMaterialTypeByCode(learningMaterialTypes.getCode());
			if (learningMaterialType != null) {
				throw new Exception("Kode tipe bahan ajar tidak boleh sama!");
			} else {
				if (learningMaterialTypes.getLearningMaterialTypeName() == null
						|| learningMaterialTypes.getLearningMaterialTypeName().trim().equals("")) {
					throw new Exception("Nama tipe bahan ajar tidak boleh kosong!");
				}
			}
		}
	}

	private void validateUpdate(LearningMaterialTypes learningMaterialTypes) throws Exception {
		if (learningMaterialTypes.getId() == null || learningMaterialTypes.getId().trim().equals("")) {
			throw new Exception("Id tipe bahan ajar tidak boleh kosong!");
		} else {
			LearningMaterialTypes learningType = getLearningMaterialTypeById(learningMaterialTypes.getId());
			if (learningType == null) {
				throw new Exception("Id tipe bahan ajar tidak ada!");
			} else {
				if (learningType.getVersion() == null) {
					throw new Exception("Tipe bahan ajar version tidak boleh kosong!");
				} else {
					if (learningMaterialTypes.getVersion() != learningType.getVersion()) {
						throw new Exception("Tipe bahan ajar version tidak sama!");
					} else {
						if (learningMaterialTypes.getCode() == null
								|| learningMaterialTypes.getCode().trim().equals("")) {
							throw new Exception("Kode tipe bahan ajar tidak boleh kosong!");
						} else {
							LearningMaterialTypes learningMaterialType = getLearningMaterialTypeByCode(
									learningMaterialTypes.getCode());
							if (learningMaterialType != null) {
								if(!learningMaterialType.getCode().equals(learningMaterialTypes.getCode())) {
									throw new Exception("Kode tipe bahan tidak boleh sama!");									
								}
							} else {
								if (learningMaterialTypes.getLearningMaterialTypeName() == null
										|| learningMaterialTypes.getLearningMaterialTypeName().trim().equals("")) {
									throw new Exception("Name tipe bahan ajar tidak boleh kosong!");
								}
							}
						}
					}
				}
			}
		}
	}
	
	private boolean validateDelete(String id) throws Exception {
		List<?> listObj = learnMaterialTypeDao.validateDeleteLearningMaterialType(id);
		listObj.forEach(System.out::println);
		List<?> list =  listObj.stream().filter(val -> val != null)
				.collect(Collectors.toList());
		System.out.println(list.size());
		return list.size() > 0 ? true : false;
	}
}