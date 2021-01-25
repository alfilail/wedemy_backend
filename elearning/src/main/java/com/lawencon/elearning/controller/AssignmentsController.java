package com.lawencon.elearning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lawencon.elearning.helper.ClassesHelper;
import com.lawencon.elearning.model.Assignments;
import com.lawencon.elearning.model.Classes;
import com.lawencon.elearning.service.AssignmentsService;

@RestController
@RequestMapping("assignment")
public class AssignmentsController {
	
	@Autowired
	AssignmentsService assignmentsService;
	
	@GetMapping("all")
	public ResponseEntity<?> getAllAssignments() {
		try {
			List<Assignments> assignment = assignmentsService.getAllAssignments();
			return new ResponseEntity<>(assignment, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getAssignmentById(@PathVariable("id") String id) {
		try {
			Assignments assignment = assignmentsService.getAssignmentsById(id);
			return new ResponseEntity<>(assignment, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> insertAssignments(@RequestPart String body, 
			@RequestPart("file") MultipartFile file) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			Assignments assignment = obj.readValue(body, Assignments.class);
			assignmentsService.insertAssignment(assignment, file);
			return new ResponseEntity<>(assignment, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

}
