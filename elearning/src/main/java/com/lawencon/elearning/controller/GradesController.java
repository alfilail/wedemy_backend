package com.lawencon.elearning.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.elearning.model.Grades;
import com.lawencon.elearning.service.GradesService;
import com.lawencon.elearning.util.MessageStat;

@RestController
@RequestMapping("grade")
public class GradesController extends ElearningBaseController {
	@Autowired
	private GradesService gradesService;

	@PostMapping
	public ResponseEntity<?> insertGrade(@RequestBody String body) {
		Grades grade = new Grades();
		try {
			grade = new ObjectMapper().readValue(body, Grades.class);
			gradesService.insertGrade(grade);
			return responseSuccess(grade, HttpStatus.CREATED, MessageStat.SUCCESS_CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getGradeById(@PathVariable("id") String id) {
		Grades grade = new Grades();
		try {
			grade = gradesService.getGradeById(id);
			return responseSuccess(grade, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllGrades() {
		List<Grades> gradesList = new ArrayList<Grades>();
		try {
			gradesList = gradesService.getAllGrades();
			return responseSuccess(gradesList, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@DeleteMapping
	public ResponseEntity<?> deleteGradeById(@RequestParam("id") String id, 
			@RequestParam("idUser") String idUser) {
		try {
			gradesService.deleteGradeById(id, idUser);
			return responseSuccess(null, HttpStatus.OK, MessageStat.SUCCESS_DELETE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@PutMapping
	public ResponseEntity<?> updateGrade(@RequestBody String body) {
		try {
			Grades grade = new ObjectMapper().readValue(body, Grades.class);
			gradesService.updateGrades(grade);
			return responseSuccess(grade, HttpStatus.CREATED, MessageStat.SUCCESS_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}
}
