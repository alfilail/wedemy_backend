package com.lawencon.elearning.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.ModuleRegistrations;
import com.lawencon.elearning.util.HibernateUtils;
import com.lawencon.util.Callback;

@Repository
public class ModuleRegistrationsDaoImpl extends ElearningBaseDaoImpl<ModuleRegistrations> implements ModuleRegistrationsDao{
	@Override
	public void insertModuleRegistration(ModuleRegistrations moduleRegistration, Callback before) throws Exception {
		save(moduleRegistration, before, null);
	}

	@Override
	public ModuleRegistrations getByIdDetailClassAndIdModuleRegistration(String idDtlClass, String idModRegist)
			throws Exception {
		String sql = sqlBuilder("SELECT id, version FROM t_r_module_registrations WHERE id_detail_class = ?1 and id_module = ?2").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, idDtlClass)
				.setParameter(2, idModRegist).getResultList();
		return HibernateUtils.bMapperList(listObj, ModuleRegistrations.class, "id", "version").get(0);
	}
}