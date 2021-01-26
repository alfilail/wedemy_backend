package com.lawencon.elearning.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.helper.ReportPresences;
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
	public List<?> getPresenceReport() throws Exception {
		String query = sqlBuilder(
				"select  tmp.fullname, (select count(tma.\"name\") from t_m_approvements where code = 'rcv')",
				" as present_day, tmm.module_name from t_r_presences trp ",
				" inner join t_m_users tmu on trp.id_user = tmu.id ",
				" inner join t_m_profiles tmp  on tmu.id_profile = tmp.id ",
				" inner join t_m_approvements tma on trp.id_approvement = tma.id ",
				" inner join t_r_detail_module_registrations trdmr on trp.id_detail_module_rgs = trdmr.id ",
				" inner join t_r_module_registrations trmr on trdmr.id_module_rgs = trmr.id ",
				" inner join t_m_detail_classes tmdc on trmr.id_detail_class = tmdc.id ",
				" inner join t_m_modules tmm on trmr.id_module = tmm.id ",
				" inner join t_m_classes tmc on tmdc.id_class = tmc.id ",
				" where trdmr.schedule_date between '2021-02-03' and '2021-02-04' ",
				" group  by tmp.fullname, tmm.module_name").toString();
		List<ReportPresences> listReportPresences = new ArrayList<>();
		List<?> listObj = createNativeQuery(query).getResultList();
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
			listReportPresences.add(reportPresences);
		});
		return listReportPresences;
	}

}
