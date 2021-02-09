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
		save(presence, before, null);
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
		List<Presences> presence = createQuery("FROM Presences WHERE code =?1", Presences.class).setParameter(1, code)
				.getResultList();
		return resultCheck(presence);
	}

	@Override
	public Presences doesTutorPresent(String idDtlModuleRgs) throws Exception {
		List<Presences> listResult = new ArrayList<>();
		String sql = sqlBuilder("SELECT pr.id_user FROM t_r_presences pr INNER JOIN t_m_users u ON pr.id_user = u.id ",
				"INNER JOIN t_m_profiles p ON u.id_profile = p.id INNER JOIN t_m_roles r ON u.id_role = r.id ",
				"WHERE pr.id_detail_module_rgs = ?1 AND r.code = 'TTR' ").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, idDtlModuleRgs).getResultList();
		listObj.forEach(val -> {
			Object obj = (Object) val;
			Presences presence = new Presences();
			presence.setId((String) obj);
			listResult.add(presence);
		});
		return listResult.size() > 0 ? listResult.get(0) : null;
	}

	@Override
	public Presences doesParticipantPresent(String idDtlModuleRgs, String idParticipant) throws Exception {
		List<Presences> listResult = new ArrayList<>();
		String sql = sqlBuilder("SELECT pr.id_user FROM t_r_presences pr ",
				" INNER JOIN t_m_users u ON pr.id_user = u.id ",
				" INNER JOIN t_m_profiles p ON u.id_profile = p.id ",
				" INNER JOIN t_m_roles r ON u.id_role = r.id ",
				" WHERE pr.id_detail_module_rgs = ?1 AND pr.id_user = ?2").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, idDtlModuleRgs).setParameter(2, idParticipant)
				.getResultList();
		listObj.forEach(val -> {
			Object objArr = (Object) val;
			Presences presence = new Presences();
			presence.setId((String) objArr);
			listResult.add(presence);
		});
		return listResult.size() > 0 ? listResult.get(0) : null;
	}

	@Override
	public List<?> getPresenceReport(String idClass, LocalDate scheduleDateStart, LocalDate scheduleDateEnd)
			throws Exception {
		String query = sqlBuilder(" SELECT tmp.fullname, tmm.module_name , ",
				" tmc.class_name, ROUND(COUNT(tar.id_presence)/CAST((SELECT COUNT(order_number) ",
				" FROM t_r_detail_module_registrations trdmr ",
				" INNER JOIN t_r_module_registrations trmr ON trdmr.id_module_rgs = trmr.id ",
				" INNER JOIN t_m_modules tmm2 ON trmr.id_module = tmm2.id ",
				" WHERE tmm2.module_name = tmm.module_name) AS decimal), 4) * 100 ",
				" AS present_day ",
				" FROM t_r_approvement_renewal tar ", 
				" INNER JOIN t_r_presences trp ON tar.id_presence = trp.id ",
				" INNER JOIN t_m_users tmu ON trp.id_user = tmu.id ",
				" INNER JOIN t_m_profiles tmp  ON tmu.id_profile = tmp.id ",
				" INNER JOIN t_r_detail_module_registrations trdmr ON trp.id_detail_module_rgs = trdmr.id ",
				" INNER JOIN t_r_module_registrations trmr ON trdmr.id_module_rgs = trmr.id ",
				" INNER JOIN t_m_detail_classes tmdc  ON trmr.id_detail_class = tmdc.id ",
				" INNER JOIN t_m_modules tmm ON trmr.id_module = tmm.id ",
				" INNER JOIN t_m_learning_materials tmlm ON tmlm.id = trdmr.id_learning_material ",
				" INNER JOIN t_m_classes tmc ON tmdc.id_class = tmc.id ",
				" WHERE trdmr.schedule_date between ?1 and ?2 ",
				" AND tmdc.id_class = ?3 AND id_approvement = ",
				" (SELECT id FROM t_m_approvements WHERE code = 'RCV') ",
				" GROUP BY tmp.fullname, tmm.module_name, tmc.class_name ",
				" ORDER BY tmm.module_name, tmp.fullname")
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
