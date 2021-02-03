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
public class GradesController {
	@Autowired
	private GradesService gradesService;

	@PostMapping
	public Response<?> insertGrade(@RequestBody String body) {
		try {
			Grades grade = new ObjectMapper().readValue(body, Grades.class);
			gradesService.insertGrade(grade);
//			return new ResponseEntity<>(grade, HttpStatus.CREATED);
			return new Response<>(true, HttpStatus.CREATED.toString(), MessageStat.SUCCESS_CREATED.msg, grade);
		} catch (Exception e) {
			e.printStackTrace();
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			return new Response<>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), MessageStat.FAILED.msg, null);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getGradeById(@PathVariable("id") String id) {
		Grades grade = new Grades();
		try {
			grade = gradesService.getGradeById(id);
			Response<Grades> res = new Response(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, grade);
			return new ResponseEntity<>(res, HttpStatus.OK);
			
//			return new Response<>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, grade);
			
		} catch (Exception e) {
			e.printStackTrace();
			Response<Grades> res = new Response(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), MessageStat.FAILED.msg, grade);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
//			return new Response<>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), MessageStat.FAILED.msg, null);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllGrades() {
		List<Grades> gradesList = new ArrayList<Grades>();
		try {
			gradesList = gradesService.getAllGrades();
			Response<Grades> res = new Response(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, gradesList);
			return new ResponseEntity<>(res, HttpStatus.OK);
//			return new Response<>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, gradesList);
		} catch (Exception e) {
			e.printStackTrace();
			Response<Grades> res = new Response(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), MessageStat.FAILED.msg, gradesList);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
//			return new Response<>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), MessageStat.FAILED.msg, null);
		}
	}

	@DeleteMapping
	public Response<?> deleteGradeById(@RequestParam("id") String id, 
			@RequestParam("idUser") String idUser) {
		try {
			gradesService.deleteGradeById(id, idUser);
//			return new ResponseEntity<>(HttpStatus.OK);
			return new Response<>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_DELETE.msg, null);
//		} catch (PersistenceException pe) {
//			pe.printStackTrace();
//			return new ResponseEntity<>("Data used in another table", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			return new Response<>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), MessageStat.FAILED.msg, null);
		}
	}

	@PutMapping
	public Response<?> updateGrade(@RequestBody String body) {
		try {
			Grades grade = new ObjectMapper().readValue(body, Grades.class);
			gradesService.updateGrades(grade);
//			return new ResponseEntity<>(grade, HttpStatus.OK);
			return new Response<>(true, HttpStatus.CREATED.toString(), MessageStat.SUCCESS_UPDATE.msg, grade);
		} catch (Exception e) {
			e.printStackTrace();
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			return new Response<>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), MessageStat.FAILED.msg, null);
		}
	}
}
