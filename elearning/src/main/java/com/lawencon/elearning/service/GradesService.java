package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.model.Grades;

public interface GradesService {
	void insertGrade(Grades grade) throws Exception;

	List<Grades> getAllGrades() throws Exception;

	Grades getGradeById(String id) throws Exception;

	Grades getGradeByScore(Double score) throws Exception;

	void deleteGradeById(String id) throws Exception;

	void updateGrades(Grades grade) throws Exception;

	Grades getGradeByCode(String code) throws Exception;
}
