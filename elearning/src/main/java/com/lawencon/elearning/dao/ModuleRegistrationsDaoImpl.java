package com.lawencon.elearning.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.ModuleRegistrations;
import com.lawencon.elearning.model.Modules;
import com.lawencon.elearning.util.HibernateUtils;
import com.lawencon.util.Callback;

@Repository
public class ModuleRegistrationsDaoImpl extends ElearningBaseDaoImpl<ModuleRegistrations>
		implements ModuleRegistrationsDao {

	@Override
	public void insertModuleRegistration(ModuleRegistrations moduleRegistration, Callback before) throws Exception {
		save(moduleRegistration, before, null);
	}

	@Override
	public ModuleRegistrations getByIdDetailClassAndIdModuleRegistration(String idDtlClass, String idModRegist)
			throws Exception {
		String sql = sqlBuilder(
				"SELECT id, version FROM t_r_module_registrations WHERE id_detail_class = ?1 and id_module = ?2")
						.toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, idDtlClass).setParameter(2, idModRegist)
				.getResultList();
		return HibernateUtils.bMapperList(listObj, ModuleRegistrations.class, "id", "version").get(0);
	}

	@Override
	public List<ModuleRegistrations> getByIdClass(String idClass) throws Exception {
		List<ModuleRegistrations> listResult = new ArrayList<>();
		String sql = sqlBuilder("SELECT mr.id, m.id idmodule, m.code, m.module_name FROM t_r_module_registrations mr ",
				"INNER JOIN t_m_modules m ON mr.id_module = m.id ",
				"INNER JOIN t_m_detail_classes dc ON mr.id_detail_class = dc.id WHERE dc.id = ?1").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, idClass).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			ModuleRegistrations moduleRgs = new ModuleRegistrations();
			moduleRgs.setId((String) objArr[0]);
			Modules module = new Modules();
			module.setId((String) objArr[1]);
			module.setCode((String) objArr[2]);
			module.setModuleName((String) objArr[3]);
			moduleRgs.setIdModule(module);
			listResult.add(moduleRgs);
		});
		return listResult;
	}

}