package com.lawencon.elearning.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.elearning.model.ClassEnrollments;
import com.lawencon.elearning.service.ClassEnrollmentService;

@RestController
@RequestMapping("class-enrollment")
public class ClassEnrollmentController {
	@Autowired
	private ClassEnrollmentService classEnrollmentService;
	
	@PostMapping
	public ResponseEntity<?> insertClassEnrollment(@RequestBody String body) {
		try {
			ClassEnrollments classEnrollment = new ObjectMapper().readValue(body, ClassEnrollments.class);
			classEnrollmentService.insertClassEnrollment(classEnrollment);
			return new ResponseEntity<>(classEnrollment, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getClassEnrollmentById(@PathVariable("id") String id) {
		ClassEnrollments classEnrollment = new ClassEnrollments();
		try {
			classEnrollment = classEnrollmentService.getClassEnrollmentsById(id);
			return new ResponseEntity<>(classEnrollment, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllClassEnrollments() {
		List<ClassEnrollments> classEnrollmentList = new ArrayList<ClassEnrollments>();
		try {
			classEnrollmentList = classEnrollmentService.getAllClassEnrollments();
			return new ResponseEntity<>(classEnrollmentList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteClassEnrollmentById(@PathVariable("id") String id) {
		try {
			classEnrollmentService.deleteClassEnrollmentsById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			return new ResponseEntity<>("Data used in another table", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping
	public ResponseEntity<?> updateClassEnrollment(@RequestBody String body) {
		try {
			ClassEnrollments classEnrollment = new ObjectMapper().readValue(body, ClassEnrollments.class);
			classEnrollmentService.updateClassEnrollments(classEnrollment);
			return new ResponseEntity<>(classEnrollment, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
