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
import com.lawencon.elearning.model.AssignmentTypes;
import com.lawencon.elearning.service.AssignmentTypesService;

@RestController
@RequestMapping("assignment-types")
public class AssignmentTypesController {
	@Autowired
	private AssignmentTypesService assignmentTypesService;

	@PostMapping
	public ResponseEntity<?> insertAssignmentType(@RequestBody String body) {
		try {
			AssignmentTypes assignmentType = new ObjectMapper().readValue(body, AssignmentTypes.class);
			assignmentTypesService.insertAssignmentType(assignmentType);
			return new ResponseEntity<>(assignmentType, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getAssignmentTypeById(@PathVariable("id") String id) {
		AssignmentTypes assignmentType = new AssignmentTypes();
		try {
			assignmentType = assignmentTypesService.getAssignmentTypeById(id);
			return new ResponseEntity<>(assignmentType, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllAsignmentTypes() {
		List<AssignmentTypes> assignmentTypesList = new ArrayList<AssignmentTypes>();
		try {
			assignmentTypesList = assignmentTypesService.getAllAssignmentTypes();
			return new ResponseEntity<>(assignmentTypesList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteAssignmentTypeById(@PathVariable("id") String id) {
		try {
			assignmentTypesService.deleteAssignmentTypeById(id);
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
	public ResponseEntity<?> updateAssignmentType(@RequestBody String body) {
		try {
			AssignmentTypes assignmentType = new ObjectMapper().readValue(body, AssignmentTypes.class);
			assignmentTypesService.updateAssignmentType(assignmentType);
			return new ResponseEntity<>(assignmentType, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
