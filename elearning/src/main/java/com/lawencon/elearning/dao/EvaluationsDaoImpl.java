package com.lawencon.elearning.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.AssignmentSubmissions;
import com.lawencon.elearning.model.DetailModuleRegistrations;
import com.lawencon.elearning.model.Evaluations;
import com.lawencon.elearning.model.ModuleRegistrations;
import com.lawencon.elearning.model.Modules;
import com.lawencon.elearning.model.Profiles;
import com.lawencon.elearning.model.Users;
import com.lawencon.util.Callback;

/**
 * @author Nur Alfilail
 */

@Repository
public class EvaluationsDaoImpl extends ElearningBaseDaoImpl<Evaluations> implements EvaluationsDao {

	@Override
	public void insertEvaluation(Evaluations evaluation, Callback before) throws Exception {
		save(evaluation, before, null, true, true);
	}

	@Override
	public List<Evaluations> getAllEvaluations() throws Exception {
		return getAll();
	}

	@Override
	public Evaluations getEvaluationById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public Evaluations getEvaluationByCode(String code) throws Exception {
		Evaluations evaluation = createQuery("FROM Evaluations WHERE code = ?1", Evaluations.class)
				.setParameter(1, code).getSingleResult();
		return evaluation;
	}

	@Override
	public String getParticipantEmail(Evaluations evaluation) throws Exception {
		Profiles participant = new Profiles();
		String sql = sqlBuilder("SELECT p.email FROM t_r_evaluations e INNER JOIN t_r_assignment_submissions asm ",
				"ON e.id_assignment_submission = asm.id INNER JOIN t_m_users u ON asm.id_participant = u.id ",
				"INNER JOIN t_m_profiles p ON u.id_profile = p.id WHERE asm.id_participant =?1").toString();
		List<?> listObj = createNativeQuery(sql)
				.setParameter(1, evaluation.getIdAssignmentSubmission().getIdParticipant().getId()).getResultList();
		listObj.forEach(val -> {
			Object obj = (Object) val;
			participant.setEmail((String) obj);
		});
		return participant.getEmail();
	}
	
	@Override
	public List<?> reportAllScore() throws Exception {
		String sql = sqlBuilder("SELECT p.fullname, p.email, p.address, p.phone, m.code, m.module_name, AVG(e.score) ",
				" FROM t_r_evaluations e ",
				" INNER JOIN t_r_assignment_submissions ams ON ams.id = e.id_assignment_submission ",
				" INNER JOIN t_m_users u ON u.id = ams.id_participant ",
				" INNER JOIN t_m_profiles p ON p.id = u.id_profile ",
				" INNER JOIN t_r_detail_module_registrations dmr ON dmr.id = ams.id_dtl_module_rgs ",
				" INNER JOIN t_r_module_registrations mr ON mr.id = dmr.id_module_rgs ",
				" INNER JOIN t_m_modules m ON m.id = mr.id_module ",
				" GROUP BY p.fullname, p.email, p.address, p.phone, m.code, m.module_name").toString();
		List<?> listObj = createNativeQuery(sql).getResultList();
		List<Evaluations> listEvaluations = new ArrayList<>();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Profiles profile = new Profiles();
			profile.setFullName((String) objArr[0]);
			profile.setEmail((String) objArr[1]);
			profile.setAddress((String) objArr[2]);
			profile.setPhone((String) objArr[3]);
			
			Users user = new Users();
			user.setIdProfile(profile);
			
			Modules module = new Modules();
			module.setCode((String) objArr[4]);
			module.setModuleName((String) objArr[5]);
			
			ModuleRegistrations modRegist = new ModuleRegistrations();
			modRegist.setIdModule(module);
			
			DetailModuleRegistrations dtlModRegist = new DetailModuleRegistrations();
			dtlModRegist.setIdModuleRegistration(modRegist);
			
			AssignmentSubmissions assignmentSubmission = new AssignmentSubmissions();
			assignmentSubmission.setIdParticipant(user);
			assignmentSubmission.setIdDetailModuleRegistration(dtlModRegist);
			
			Evaluations evaluation = new Evaluations();
			evaluation.setIdAssignmentSubmission(assignmentSubmission);
			evaluation.setScore((Double) objArr[6]);
			
			listEvaluations.add(evaluation);
		});
		return listEvaluations;
	}

	@Override
	public List<?> reportScore(String idDtlClass, String idParticipant) throws Exception {
		String sql = sqlBuilder("SELECT p.fullname, p.email, p.address, p.phone, m.code, m.module_name, avg(e.score) ",
				" FROM t_r_evaluations e ",
				" INNER JOIN t_r_assignment_submissions ams ON ams.id = e.id_assignment_submission ",
				" INNER JOIN t_m_users u ON u.id = ams.id_participant ",
				" INNER JOIN t_m_profiles p ON p.id = u.id_profile ",
				" INNER JOIN t_r_detail_module_registrations dmr ON dmr.id = ams.id_dtl_module_rgs ",
				" INNER JOIN t_r_module_registrations mr ON mr.id = dmr.id_module_rgs ",
				" INNER JOIN t_m_modules m ON m.id = mr.id_module ",
				" WHERE mr.id_detail_class = ?1 and ams.id_participant = ?2 ",
				" GROUP BY p.fullname, p.email, p.address, p.phone, m.code, m.module_name").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, idDtlClass).setParameter(2, idParticipant).getResultList();
		List<Evaluations> listEvaluations = new ArrayList<>();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Profiles profile = new Profiles();
			profile.setFullName((String) objArr[0]);
			profile.setEmail((String) objArr[1]);
			profile.setAddress((String) objArr[2]);
			profile.setPhone((String) objArr[3]);
			
			Users user = new Users();
			user.setIdProfile(profile);
			
			Modules module = new Modules();
			module.setCode((String) objArr[4]);
			module.setModuleName((String) objArr[5]);
			
			ModuleRegistrations modRegist = new ModuleRegistrations();
			modRegist.setIdModule(module);
			
			DetailModuleRegistrations dtlModRegist = new DetailModuleRegistrations();
			dtlModRegist.setIdModuleRegistration(modRegist);
			
			AssignmentSubmissions assignmentSubmission = new AssignmentSubmissions();
			assignmentSubmission.setIdParticipant(user);
			assignmentSubmission.setIdDetailModuleRegistration(dtlModRegist);
			
			Evaluations evaluation = new Evaluations();
			evaluation.setIdAssignmentSubmission(assignmentSubmission);
			evaluation.setScore((Double) objArr[6]);
			
			listEvaluations.add(evaluation);
		});
		return listEvaluations;
	}

}
