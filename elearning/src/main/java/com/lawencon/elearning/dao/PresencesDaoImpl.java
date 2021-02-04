package com.lawencon.elearning.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.helper.ReportPresences;
import com.lawencon.elearning.model.Classes;
import com.lawencon.elearning.model.Modules;
import com.lawencon.elearning.model.Presences;
import com.lawencon.elearning.model.Profiles;
import com.lawencon.util.Callback;

@Repository
public class PresencesDaoImpl extends ElearningBaseDaoImpl<Presences> implements PresencesDao {

	@Override
	public void insertPresence(Presences presence, Callback before) throws Exception {
		save(presence, before, null, true, true);
	}

	@Override
	public List<Presences> getAllPresences() throws Exception {
		return getAll();
	}

	@Override
	public Presences getPresenceById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public void updatePresence(Presences presence, Callback before) throws Exception {
		save(presence, before, null, true, true);
	}

	@Override
	public void deletePresenceById(String id) throws Exception {
		deleteById(id);
	}

	@Override
	public Presences getPresenceByCode(String code) throws Exception {
		Presences presence = createQuery("FROM Presences WHERE code =?1", Presences.class).setParameter(1, code)
				.getSingleResult();
		return presence;
	}

	@Override
	public Presences doesTutorPresent(String idDtlModuleRgs) throws Exception {
		List<Presences> listResult = new ArrayList<>();
		String sql = sqlBuilder("SELECT pr.id_user FROM t_r_presences pr INNER JOIN t_m_users u ON pr.id_user = u.id ",
				"INNER JOIN t_m_profiles p ON u.id_profile = p.id INNER JOIN t_m_roles r ON u.id_role = r.id ",
				"WHERE pr.id_detail_module_rgs = ?1 AND r.code = 'TTR' ").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, idDtlModuleRgs).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Presences presence = new Presences();
			presence.setId((String) objArr[0]);
			listResult.add(presence);
		});
		return listResult.size() > 0 ? listResult.get(0) : null;
	}

	@Override
	public List<?> getPresenceReport(String idClass, LocalDate scheduleDateStart, LocalDate scheduleDateEnd)
			throws Exception {
		String query = sqlBuilder(" select tmp.fullname, tmm.module_name , tmc.class_name, ",
				" round(count(tar.id_presence) ", " /cast((select count(order_number) ",
				" from t_r_detail_module_registrations trdmr ", " inner join t_r_module_registrations trmr ",
				" on trdmr.id_module_rgs = trmr.id ", " inner join t_m_modules tmm2 on trmr.id_module = tmm2.id ",
				" where tmm2.module_name = tmm.module_name) as decimal), 4) * 100 ", " as present_day ",
				" from t_r_approvement_renewal tar ", " inner join t_r_presences trp on tar.id_presence = trp.id ",
				" inner join t_m_users tmu on trp.id_user = tmu.id ",
				" inner join t_m_profiles tmp  on tmu.id_profile = tmp.id ",
				" inner join t_r_detail_module_registrations trdmr on ", " trp.id_detail_module_rgs = trdmr.id ",
				" inner join t_r_module_registrations trmr ", " on trdmr.id_module_rgs = trmr.id ",
				" inner join t_m_detail_classes tmdc ", " on trmr.id_detail_class = tmdc.id ",
				" inner join t_m_modules tmm on trmr.id_module = tmm.id ", " inner join t_m_learning_materials tmlm ",
				" on tmlm.id = trdmr.id_learning_material ", " inner join t_m_classes tmc on tmdc.id_class = tmc.id ",
				" where trdmr.schedule_date between ?1 and ?2 and ", " tmdc.id_class = ?3 and id_approvement = ",
				" (select id from t_m_approvements where code = 'RCV') ",
				" group  by tmp.fullname, tmm.module_name, tmc.class_name ", " order by tmm.module_name, tmp.fullname")
						.toString();
		List<ReportPresences> listReportPresences = new ArrayList<>();
		List<?> listObj = createNativeQuery(query).setParameter(1, scheduleDateStart).setParameter(2, scheduleDateEnd)
				.setParameter(3, idClass).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Profiles profile = new Profiles();
			profile.setFullName((String) objArr[0]);
			ReportPresences reportPresences = new ReportPresences();
			reportPresences.setFullname(profile);
			Modules module = new Modules();
			module.setModuleName((String) objArr[1]);
			reportPresences.setModule(module);
			Classes clazz = new Classes();
			clazz.setClassName((String) objArr[2]);
			reportPresences.setClazz(clazz);
			reportPresences.setPresentDay(Double.valueOf(objArr[3].toString()));
			listReportPresences.add(reportPresences);
		});
		return listReportPresences;
	}

}
