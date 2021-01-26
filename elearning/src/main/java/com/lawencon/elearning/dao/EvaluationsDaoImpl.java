package com.lawencon.elearning.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.Evaluations;
import com.lawencon.elearning.model.Profiles;
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

}
