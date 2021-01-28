package com.lawencon.elearning.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.elearning.model.Grades;
import com.lawencon.elearning.util.HibernateUtils;
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
		Grades grade = createQuery("FROM Grades WHERE code = ?1", Grades.class).setParameter(1, code).getSingleResult();
		return grade;
	}

	@Override
	public Grades getGradeByScore(Double score) throws Exception {
		String sql = sqlBuilder("SELECT id, version FROM t_m_grades WHERE min_score <= ?1 AND max_score >= ?1").toString();
		List<?> listObj = createNativeQuery(sql).setParameter(1, score).getResultList();
		return HibernateUtils.bMapperList(listObj, Grades.class, "id", "version").get(0);
	}

	@Override
	public void deleteGradeById(String id) throws Exception {
		deleteById(id);
	}

	@Override
	public void updateGrades(Grades grade, Callback before) throws Exception {
		save(grade, before, null, true, true);
	}

}
