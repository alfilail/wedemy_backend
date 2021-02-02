package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.Grades;
import com.lawencon.util.Callback;

public interface GradesDao {
	void insertGrade(Grades assignmentType, Callback before) throws Exception;
	
	List<Grades> getAllGrades() throws Exception;
	
	Grades getGradeById(String id) throws Exception;
	
	Grades getGradeByScore(Double score) throws Exception;
	
	void updateGrades(Grades grade, Callback before) throws Exception;
	
	void deleteGradeById(String id) throws Exception;
	
	Grades getGradeByCode(String code) throws Exception;
	
	List<?> validateDeleteGrade(String id) throws Exception;
	
	void softDeleteGradeById(String id, String idUser) throws Exception;
}
