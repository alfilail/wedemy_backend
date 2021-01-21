package com.lawencon.elearning.service;

import java.util.List;

import com.lawencon.elearning.model.Grades;

public interface GradesService {
	void insertGrade(Grades grade) throws Exception;
	
	List<Grades> getAllGrades() throws Exception;
	
	Grades getGradeById (String id) throws Exception;
}
