package com.lawencon.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.elearning.model.Grades;
import com.lawencon.elearning.service.GradesService;

@RestController
@RequestMapping("/grades")
public class GradesController {
	@Autowired
	private GradesService gradesService;
	
	@PostMapping
	public ResponseEntity<?> insertGrade(@RequestBody String body) {
		try {
			Grades grade = new ObjectMapper().readValue(body, Grades.class);
			gradesService.insertGrade(grade);
			return new ResponseEntity<>(grade, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getAssignmentTypeById(@PathVariable("id") String id) {
		Grades grade = new Grades();
		try {
			grade = gradesService.getGradeById(id);
			return new ResponseEntity<>(grade, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
