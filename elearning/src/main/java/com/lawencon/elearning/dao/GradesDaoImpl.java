package com.lawencon.elearning.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.elearning.model.Grades;
import com.lawencon.util.Callback;

@Repository
public class GradesDaoImpl extends BaseDaoImpl<Grades> implements GradesDao {
	@Override
	public void insertGrade(Grades grade, Callback before) throws Exception {
		save(grade, before, null, true, true);
	}
	
	@Override
	public List<Grades> getAllGrades() throws Exception {
		return getAll();
	}
	
	public Grades getGradeById(String id) throws Exception {
		return getById(id);
	}
	
	@Override
	public Grades getGradeByCode(String code) throws Exception {
		Grades grade = createQuery("FROM Grades WHERE code = ?1", Grades.class)
				.setParameter(1, code).getSingleResult();
		return grade;
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
