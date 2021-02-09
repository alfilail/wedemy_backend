package com.lawencon.elearning.dao;

import java.util.List;

import com.lawencon.elearning.model.Grades;
import com.lawencon.util.Callback;

public interface GradesDao {
	void insert(Grades assignmentType, Callback before) throws Exception;
	
	List<Grades> getAllGrades() throws Exception;
	
	Grades getGradeById(String id) throws Exception;
	
	Grades getByScore(Double score) throws Exception;
	
	void update(Grades grade, Callback before) throws Exception;
	
	void deleteById(String id) throws Exception;
	
	Grades getByCode(String code) throws Exception;
	
	List<?> validateDelete(String id) throws Exception;
	
	void softDeleteById(String id, String idUser) throws Exception;
}
