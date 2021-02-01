package com.lawencon.elearning.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.DetailClasses;
import com.lawencon.elearning.model.ModuleRegistrations;
import com.lawencon.elearning.model.Modules;
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
		List<ModuleRegistrations> listResult = new ArrayList<>();
		String sql = sqlBuilder(
				"SELECT mr.id, dc.start_date, dc.end_date FROM t_r_module_registrations INNER JOIN t_m_detail_classes dc ",
				"ON mr.id_detail_class = dc.id WHERE mr.id = ?1 AND dc.id = ?2").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, idDtlClass).setParameter(2, idModRegist)
				.getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			ModuleRegistrations moduleRgs = new ModuleRegistrations();
			moduleRgs.setId((String) objArr[0]);
			DetailClasses dtlClass = new DetailClasses();
			dtlClass.setStartDate((LocalDate) objArr[1]);
			dtlClass.setEndDate((LocalDate) objArr[2]);
			moduleRgs.setIdDetailClass(dtlClass);
			listResult.add(moduleRgs);
		});
		return listResult.size() > 0 ? listResult.get(0) : null;
	}

	@Override
	public List<ModuleRegistrations> getByIdDtlClass(String idClass) throws Exception {
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