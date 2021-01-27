package com.lawencon.elearning.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.DetailModuleRegistrations;
import com.lawencon.util.Callback;

@Repository
public class DetailModuleRegistrationsDaoImpl extends ElearningBaseDaoImpl<DetailModuleRegistrations>
		implements DetailModuleRegistrationsDao {

	@Override
	public void insertDetailModuleRegistration(DetailModuleRegistrations dtlModRegist, Callback before)
			throws Exception {
		save(dtlModRegist, before, null, true, true);
	}

	@Override
	public DetailModuleRegistrations getDetailModuleRegistrationsById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public List<DetailModuleRegistrations> getDetailModuleRegistrationsByIdModuleRgs(String idModuleRgs)
			throws Exception {
		List<DetailModuleRegistrations> listResult = new ArrayList<>();
		String sql = sqlBuilder("SELECT module_name, learning_material_name FROM t_r_detail_module_registrations dmr ",
				"INNER JOIN t_m_learning_materials lm ON dmr.id_learning_material = lm.id ",
				"INNER JOIN t_r_module_registrations mr ON dmr.id_learning_material = mr.id ",
				"INNER JOIN t_m_modules m ON mr.id_module = m.id ",
				"INNER JOIN t_m_detail_classes dc ON mr.id_detail_class = dc.id ",
				"INNER JOIN t_m_classes c ON dc.id_class = c.id ",
				"INNER JOIN t_r_class_enrollments ce ON ce.id_detail_class = dc.id ", "WHERE c.id = ?1 GROUP BY ")
						.toString();
		return null;
	}

}
