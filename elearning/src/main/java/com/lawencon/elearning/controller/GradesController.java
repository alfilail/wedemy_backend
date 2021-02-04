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
import com.lawencon.elearning.helper.Response;
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
			Response<Grades> res = new Response<Grades>(true, HttpStatus.CREATED.toString(), MessageStat.SUCCESS_CREATED.msg, grade);
			return new ResponseEntity<>(res, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			Response<Grades> res = new Response<Grades>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), grade);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getGradeById(@PathVariable("id") String id) {
		Grades grade = new Grades();
		try {
			grade = gradesService.getGradeById(id);
			Response<Grades> res = new Response<Grades>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, grade);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<Grades> res = new Response<Grades>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), grade);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllGrades() {
		List<Grades> gradesList = new ArrayList<Grades>();
		try {
			gradesList = gradesService.getAllGrades();
			Response<List<Grades>> res = new Response<List<Grades>>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, gradesList);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<List<Grades>> res = new Response<List<Grades>>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), gradesList);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping
	public ResponseEntity<?> deleteGradeById(@RequestParam("id") String id, 
			@RequestParam("idUser") String idUser) {
		try {
			gradesService.deleteGradeById(id, idUser);
			Response<Grades> res = new Response<Grades>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_DELETE.msg, null);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<Grades> res = new Response<Grades>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping
	public ResponseEntity<?> updateGrade(@RequestBody String body) {
		try {
			Grades grade = new ObjectMapper().readValue(body, Grades.class);
			gradesService.updateGrades(grade);
			Response<Grades> res = new Response<Grades>(true, HttpStatus.CREATED.toString(), MessageStat.SUCCESS_UPDATE.msg, grade);
			return new ResponseEntity<>(res, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			Response<Grades> res = new Response<Grades>(true, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
