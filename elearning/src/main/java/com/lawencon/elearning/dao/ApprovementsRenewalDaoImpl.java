package com.lawencon.elearning.dao;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.Approvements;
import com.lawencon.elearning.model.ApprovementsRenewal;
import com.lawencon.elearning.model.Presences;
import com.lawencon.elearning.model.Profiles;
import com.lawencon.elearning.model.Users;
import com.lawencon.elearning.util.EmptyField;
import com.lawencon.util.Callback;

@Repository
public class ApprovementsRenewalDaoImpl extends ElearningBaseDaoImpl<ApprovementsRenewal>
		implements ApprovementsRenewalDao {

	@Override
	public void insertApprovementsRenewal(ApprovementsRenewal approvementsRenewal, Callback before) throws Exception {
		save(approvementsRenewal, before, null);
	}

	@Override
	public void participantApprovementsRenewal(ApprovementsRenewal approvementsRenewal, Callback before)
			throws Exception {
		save(approvementsRenewal, before, null, true, true);
	}

	@Override
	public List<ApprovementsRenewal> getAllApprovementsRenewal() throws Exception {
		return getAll();
	}

	@Override
	public List<ApprovementsRenewal> getListParticipantsPresence(String idDtlClass, String idDtlModuleRgs)
			throws Exception {
		List<ApprovementsRenewal> listResult = new ArrayList<>();
		String sql = sqlBuilder("SELECT pr.fullname, p.id, p.presence_time, (SELECT a.code FROM ",
				"t_r_approvement_renewal ar LEFT JOIN t_m_approvements a ON ar.id_approvement = a.id ",
				"WHERE ar.id_presence = p.id ORDER BY ar.created_at DESC LIMIT 1) ",
				"FROM t_r_class_enrollments ce INNER JOIN t_m_users u ON ce.id_user = u.id ",
				"INNER JOIN t_m_profiles pr ON u.id_profile = pr.id INNER JOIN t_m_detail_classes dc ",
				"ON ce.id_detail_class = dc.id INNER JOIN t_r_module_registrations mr ON dc.id = mr.id_detail_class ",
				"INNER JOIN t_r_detail_module_registrations dmr ON mr.id = dmr.id_module_rgs ",
				"LEFT JOIN t_r_presences p ON dmr.id = p.id_detail_module_rgs AND ce.id_user = p.id_user ",
				"WHERE ce.id_detail_class =?1 and dmr.id =?2 ORDER BY p.presence_time ASC").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, idDtlClass).setParameter(2, idDtlModuleRgs)
				.getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Profiles profile = new Profiles();
			profile.setFullName((String) objArr[0]);
			Users user = new Users();
			user.setIdProfile(profile);
			Presences presence = new Presences();
			presence.setIdUser(user);
			presence.setId(objArr[1] != null ? (String) objArr[1] : EmptyField.EMPTY.msg);
			presence.setPresenceTime(objArr[2] != null ? ((Time) objArr[2]).toLocalTime() : null);
			Approvements approvement = new Approvements();
			approvement.setCode(objArr[3] != null ? (String) objArr[3] : EmptyField.EMPTY.msg);
			ApprovementsRenewal approvementRenewal = new ApprovementsRenewal();
			approvementRenewal.setIdPresence(presence);
			approvementRenewal.setIdApprovement(approvement);
			listResult.add(approvementRenewal);
		});
		return listResult;
	}

	@Override
	public ApprovementsRenewal getApprovementsRenewalById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public ApprovementsRenewal checkParticipantPresence(String idDtlModuleRgs, String idUser) throws Exception {
		List<ApprovementsRenewal> listResult = new ArrayList<>();
		String sql = sqlBuilder("SELECT a.code FROM t_r_presences p INNER JOIN t_r_approvement_renewal ar ",
				"ON ar.id_presence = p.id INNER JOIN t_m_approvements a ON ar.id_approvement = a.id ",
				"WHERE p.id_detail_module_rgs =?1 AND p.id_user =?2 ORDER BY ar.created_at DESC LIMIT 1").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, idDtlModuleRgs).setParameter(2, idUser)
				.getResultList();
		listObj.forEach(val -> {
			Object obj = (Object) val;
			Approvements approvement = new Approvements();
			approvement.setCode((String) obj);
			ApprovementsRenewal approvementRenewal = new ApprovementsRenewal();
			approvementRenewal.setIdApprovement(approvement);
			listResult.add(approvementRenewal);
		});
		return listResult.size() > 0 ? listResult.get(0) : null;
	}

}
