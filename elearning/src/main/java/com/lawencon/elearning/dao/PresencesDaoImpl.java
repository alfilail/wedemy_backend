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
	public List<?> getPresenceReport(String idClass, LocalDate scheduleDateStart, LocalDate scheduleDateEnd) throws Exception {
		String query = sqlBuilder(
				" select  tmp.fullname, (select count(tma.approvement_name) ",
				" from t_m_approvements where code = 'rcv') ",
				" as present_day, tmm.module_name, tmc.class_name ",
				" from t_r_approvement_renewal tar ",
				" inner join t_r_presences trp on tar.id_presence = trp.id",
				" inner join t_m_users tmu on trp.id_user = tmu.id ",
				" inner join t_m_profiles tmp  on tmu.id_profile = tmp.id ",
				" inner join t_m_approvements tma on tar.id_approvement = tma.id ",
				" inner join t_r_detail_module_registrations trdmr on trp.id_detail_module_rgs = trdmr.id ",
				" inner join t_r_module_registrations trmr on trdmr.id_module_rgs = trmr.id ",
				" inner join t_m_detail_classes tmdc on trmr.id_detail_class = tmdc.id ",
				" inner join t_m_modules tmm on trmr.id_module = tmm.id ",
				" inner join t_m_classes tmc on tmdc.id_class = tmc.id ",
				" where trdmr.schedule_date between ?1 and ?2 ",
				" and tmdc.id_class = ?3",
				" group by tmp.fullname, tmm.module_name, tmc.class_name ").toString();
		List<ReportPresences> listReportPresences = new ArrayList<>();
		List<?> listObj = createNativeQuery(query).setParameter(1, scheduleDateStart)
				.setParameter(2, scheduleDateEnd).setParameter(3, idClass).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Profiles profile = new Profiles();
			profile.setFullName((String) objArr[0]);
			ReportPresences reportPresences = new ReportPresences();
			reportPresences.setFullname(profile);
			reportPresences.setPresentDay(Integer.valueOf(objArr[1].toString()));
			Modules module = new Modules();
			module.setModuleName((String) objArr[2]);
			reportPresences.setModule(module);
			Classes clazz = new Classes();
			clazz.setClassName((String) objArr[3]);
			reportPresences.setClazz(clazz);
			listReportPresences.add(reportPresences);
		});
		return listReportPresences;
	}

}
