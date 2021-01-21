package com.lawencon.elearning.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.elearning.model.Classes;
import com.lawencon.elearning.service.ClassesService;

@RestController
@RequestMapping("class")
public class ClassesController {
	
	@Autowired
	private ClassesService classesService;
	
	@GetMapping("all")
	public ResponseEntity<?> getAllClasses() {
		try {
			List<Classes> clazz = classesService.getAllClasses();
			return new ResponseEntity<>(clazz, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getClassById(@PathVariable("id") String id) {
		try {
			Classes clazz = classesService.getClassById(id);
			return new ResponseEntity<>(clazz, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> insertClass(@RequestBody String body) {
		try {
			Classes clazz = new ObjectMapper().readValue(body, Classes.class);
			classesService.insertClass(clazz);
			return new ResponseEntity<>(clazz, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping
	public ResponseEntity<?> updateClass(@RequestBody String body) {
		try {
			Classes clazz = new ObjectMapper().readValue(body, Classes.class);
			classesService.updateClass(clazz);
			return new ResponseEntity<>(clazz, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteClassById(@PathVariable("id") String id) {
		try {
			classesService.deleteClassById(id);
			return new ResponseEntity<>("Class Succesfully Deleted", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
