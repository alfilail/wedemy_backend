package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.GradesDao;
import com.lawencon.elearning.model.Grades;

@Service
public class GradesServiceImpl extends BaseServiceImpl implements GradesService {

	@Autowired
	private GradesDao gradeDao;

	@Override
	public void insertGrade(Grades grade) throws Exception {
		gradeDao.insertGrade(grade, () -> validateInsert(grade));
	}

	@Override
	public List<Grades> getAllGrades() throws Exception {
		return gradeDao.getAllGrades();
	}

	@Override
	public Grades getGradeById(String id) throws Exception {
		return gradeDao.getGradeById(id);
	}

	@Override
	public Grades getGradeByCode(String code) throws Exception {
		return gradeDao.getGradeByCode(code);
	}

	@Override
	public Grades getGradeByScore(Double score) throws Exception {
		return gradeDao.getGradeByScore(score);
	}

	@Override
	public void deleteGradeById(String id) throws Exception {
		gradeDao.deleteGradeById(id);
	}

	@Override
	public void updateGrades(Grades grade) throws Exception {
		gradeDao.updateGrades(grade, () -> validateUpdate(grade));
	}

	private void validateInsert(Grades grade) throws Exception {
		if (grade.getCode() == null || grade.getCode().trim().equals("")) {
			throw new Exception("Kode grade tidak boleh kosong");
		}
		if (grade.getMinScore() == null || grade.getMaxScore() == null) {
			throw new Exception("Minimum dan maximum score harus diisi");
		} else {
			if (grade.getMinScore() >= grade.getMaxScore()) {
				throw new Exception("Minimum score harus lebih kecil dari maximum score");
			}
		}
	}

	private void validateUpdate(Grades grade) throws Exception {
		if (grade.getId() == null || grade.getId().trim().equals("")) {
			throw new Exception("Id grade tidak boleh kosong");
		}
		if (grade.getCode() == null || grade.getCode().trim().equals("")) {
			throw new Exception("Kode grade tidak boleh kosong");
		}
		if (grade.getMinScore() == null || grade.getMaxScore() == null) {
			throw new Exception("Minimum dan maximum score harus diisi");
		} else {
			if (grade.getMinScore() >= grade.getMaxScore()) {
				throw new Exception("Minimum score harus lebih kecil dari maximum score");
			}
		}
	}

}
