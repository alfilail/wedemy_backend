package com.lawencon.elearning.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.Grades;
import com.lawencon.util.Callback;

@Repository
public class GradesDaoImpl extends ElearningBaseDaoImpl<Grades> implements GradesDao {
	@Override
	public void insertGrade(Grades grade, Callback before) throws Exception {
		save(grade, before, null, true, true);
	}

	@Override
	public List<Grades> getAllGrades() throws Exception {
		return getAll();
	}

	@Override
	public Grades getGradeById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public Grades getGradeByCode(String code) throws Exception {
		List<Grades> grade = createQuery("FROM Grades WHERE code = ?1", Grades.class).setParameter(1, code)
				.getResultList();
		return resultCheck(grade);
	}

	@Override
	public Grades getGradeByScore(Double score) throws Exception {
		List<Grades> grade = createQuery("FROM Grades WHERE minScore <= ?1 AND maxScore >= ?1", Grades.class)
				.setParameter(1, score).getResultList();
		return resultCheck(grade);
	}

	@Override
	public void deleteGradeById(String id) throws Exception {
		deleteById(id);
	}

	@Override
	public void updateGrades(Grades grade, Callback before) throws Exception {
		save(grade, before, null, true, true);
	}

	@Override
	public void softDeleteGradeById(String id, String idUser) throws Exception {
		updateNativeSQL("UPDATE t_m_grades SET is_active = false", id, idUser);
	}

	@Override
	public List<?> validateDeleteGrade(String id) throws Exception {
		String sql = sqlBuilder("select tre.id from t_m_grades tmg full join t_r_evaluations tre ",
				"on tmg.id = tre.id_grade where tmg.id = ?1").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, id).setMaxResults(1).getResultList();
		List<String> result = new ArrayList<String>();
		listObj.forEach(val -> {
			Object obj = (Object) val;
			result.add(obj != null ? obj.toString() : null);
		});
		return result;
	}

}
