package com.lawencon.elearning.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.GradesDao;
import com.lawencon.elearning.model.Grades;
import com.lawencon.util.Callback;

@Service
public class GradesServiceImpl extends BaseServiceImpl implements GradesService{
	@Autowired
	private GradesDao gradeDao;
	
	@Override
	public void insertGrade(Grades grade) throws Exception {
		Callback before = null;
		grade.setCreatedAt(LocalDateTime.now());
		grade.setUpdatedAt(LocalDateTime.now());
		gradeDao.insertGrade(grade, before);
	}
	
	@Override
	public List<Grades> getAllGrades() throws Exception {
		return gradeDao.getAllGrades();
	}
	
	@Override
	public Grades getGradeById(String id) throws Exception {
		return gradeDao.getGradeById(id);
	}
}
