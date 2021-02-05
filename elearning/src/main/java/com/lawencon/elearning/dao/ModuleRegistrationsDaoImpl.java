package com.lawencon.elearning.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.Classes;
import com.lawencon.elearning.model.DetailClasses;
import com.lawencon.elearning.model.ModuleRegistrations;
import com.lawencon.elearning.model.Modules;
import com.lawencon.elearning.model.Profiles;
import com.lawencon.elearning.model.Users;
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
		String sql = sqlBuilder("SELECT c.class_name, c.code classcode, c.description, f.file, p.fullname, ",
				"dc.code dtlclasscode, dc.start_date, dc.end_date, dc.start_time, dc.end_time, ",
				"mr.id, m.id idmodule, m.code modulecode, m.module_name FROM t_r_module_registrations mr ",
				"INNER JOIN t_m_modules m ON mr.id_module = m.id ",
				"INNER JOIN t_m_detail_classes dc ON mr.id_detail_class = dc.id ",
				"INNER JOIN t_m_classes c ON dc.id_class = c.id INNER JOIN t_m_files f ON c.id_file = f.id ",
				"INNER JOIN t_m_users u ON c.id_tutor = u.id INNER JOIN t_m_profiles p ON u.id_profile = p.id ",
				"WHERE dc.id = ?1").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, idClass).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Classes clazz = new Classes();
			clazz.setClassName((String) objArr[0]);
			clazz.setCode((String) objArr[1]);
			clazz.setDescription((String) objArr[2]);
//			Files thumbnailImg = new Files();
//			thumbnailImg.setFile((byte[]) objArr[3]);
			Profiles profile = new Profiles();
			profile.setFullName((String) objArr[4]);
			Users tutor = new Users();
			tutor.setIdProfile(profile);
//			clazz.setIdFile(thumbnailImg);
			clazz.setIdTutor(tutor);
//			DetailClasses detailClass = new DetailClasses();
//			detailClass.setCode((String) objArr[5]);
//			detailClass.setStartDate(((Date) objArr[6]).toLocalDate());
//			detailClass.setEndDate(((Date) objArr[7]).toLocalDate());
//			detailClass.setStartTime(((Time) objArr[8]).toLocalTime());
//			detailClass.setEndTime(((Time) objArr[9]).toLocalTime());
//			detailClass.setIdClass(clazz);
			ModuleRegistrations moduleRgs = new ModuleRegistrations();
			moduleRgs.setId((String) objArr[10]);
//			moduleRgs.setIdDetailClass(detailClass);
			Modules module = new Modules();
			module.setId((String) objArr[11]);
			module.setCode((String) objArr[12]);
			module.setModuleName((String) objArr[13]);
			moduleRgs.setIdModule(module);
			listResult.add(moduleRgs);
		});
		return listResult;
	}
	
	@Override
	public List<ModuleRegistrations> getIdModuleRegistrationByIdDetailClass(String idDetailClass) throws Exception {
		List<ModuleRegistrations> moduleRegistrationList = createQuery("FROM ModuleRegistrations WHERE idDetailClass.id = ?1 ", ModuleRegistrations.class).setParameter(1, idDetailClass).getResultList();
		return moduleRegistrationList.size() > 0 ? moduleRegistrationList : null;
	}

}