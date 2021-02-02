package com.lawencon.elearning.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.DetailModuleRegistrations;
import com.lawencon.elearning.model.Files;
import com.lawencon.elearning.model.LearningMaterialTypes;
import com.lawencon.elearning.model.LearningMaterials;
import com.lawencon.util.Callback;

@Repository
public class DetailModuleRegistrationsDaoImpl extends ElearningBaseDaoImpl<DetailModuleRegistrations>
		implements DetailModuleRegistrationsDao {

	@Override
	public void insertDetailModuleRegistration(DetailModuleRegistrations dtlModRegist, Callback before)
			throws Exception {
		save(dtlModRegist, before, null);
	}

	@Override
	public DetailModuleRegistrations getDetailModuleRegistrationsById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public DetailModuleRegistrations getByOrderNumber(Integer orderNumber) throws Exception {
		DetailModuleRegistrations approvement = createQuery("FROM DetailModuleRegistrations WHERE orderNumber = ?1",
				DetailModuleRegistrations.class).setParameter(1, orderNumber).getSingleResult();
		return approvement;
	}

	@Override
	public List<DetailModuleRegistrations> getDetailModuleRegistrationsByIdModuleRgs(String idModuleRgs)
			throws Exception {
		List<DetailModuleRegistrations> listResult = new ArrayList<>();
		String sql = sqlBuilder("SELECT lm.id materialid, lm.code materialcode, lm.learning_material_name, lm.description, ",
				"lmt.code typecode, f.file, dmr.id dmrid, dmr.schedule_date FROM t_r_detail_module_registrations dmr ",
				"INNER JOIN t_m_learning_materials lm ON dmr.id_learning_material = lm.id ",
				"INNER JOIN t_m_learning_material_types lmt ON lm.id_learning_material_type = lmt.id ",
				"INNER JOIN t_m_files f ON lm.id_file = f.id WHERE dmr.id_module_rgs =?1").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, idModuleRgs).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			LearningMaterials learningMaterial = new LearningMaterials();
			learningMaterial.setId((String) objArr[0]);
			learningMaterial.setCode((String) objArr[1]);
			learningMaterial.setLearningMaterialName((String) objArr[2]);
			learningMaterial.setDescription((String) objArr[3]);
			LearningMaterialTypes lmType = new LearningMaterialTypes();
			lmType.setCode((String) objArr[4]);
			learningMaterial.setIdLearningMaterialType(lmType);
			Files file = new Files();
			file.setFile((byte[]) objArr[5]);
			learningMaterial.setIdFile(file);
			DetailModuleRegistrations dtlModuleRgs = new DetailModuleRegistrations();
			dtlModuleRgs.setIdLearningMaterial(learningMaterial);
			dtlModuleRgs.setId((String) objArr[6]);
			dtlModuleRgs.setScheduleDate(((Date) objArr[7]).toLocalDate());
			listResult.add(dtlModuleRgs);
		});
		return listResult;
	}

}
