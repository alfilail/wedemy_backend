package com.lawencon.elearning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lawencon.elearning.model.AssignmentSubmissions;
import com.lawencon.elearning.service.AssignmentSubmissionsService;

@RestController
@RequestMapping("assignment-submissions")
public class AssignmentSubmissionsController {

	@Autowired
	private AssignmentSubmissionsService assignmentSubmissionsService;
	
	@GetMapping("all")
	public ResponseEntity<?> getAllClasses() {
		try {
			List<AssignmentSubmissions> assignmentSubmissions = assignmentSubmissionsService.getAllAssignmentSubmissions();
			return new ResponseEntity<>(assignmentSubmissions, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getassignmentSubmissionsById(@PathVariable("id") String id) {
		try {
			AssignmentSubmissions assignmentSubmissions = assignmentSubmissionsService.getAssignmentSubmissionsById(id);
			return new ResponseEntity<>(assignmentSubmissions, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> insertAssignmentSubmission(@RequestPart String body, 
			@RequestPart("file") MultipartFile file) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			AssignmentSubmissions assignmentSubmissions = obj.readValue(body, AssignmentSubmissions.class);
			assignmentSubmissionsService.insertAssignmentSubmissions(assignmentSubmissions, file);
			return new ResponseEntity<>(assignmentSubmissions, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PatchMapping
	public ResponseEntity<?> updateScore(@RequestBody String body) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			AssignmentSubmissions assignmentSubmissions = obj.readValue(body, AssignmentSubmissions.class);
			assignmentSubmissionsService.updateScore(assignmentSubmissions);
			return new ResponseEntity<>(assignmentSubmissions, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteClassById(@PathVariable("id") String id) {
		try {
			assignmentSubmissionsService.deleteAssignmentSubmissionsById(id);
			return new ResponseEntity<>("Class Succesfully Deleted", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
