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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lawencon.elearning.model.LearningMaterialTypes;
import com.lawencon.elearning.service.LearningMaterialTypesService;

@RestController
@RequestMapping("learning-material-type")
public class LearningMaterialTypesController {
	@Autowired
	private LearningMaterialTypesService lmTypesService;

	@PostMapping
	public ResponseEntity<?> insertLearningMaterialType(@RequestBody String body) {
		try {
			LearningMaterialTypes lmType = new ObjectMapper().readValue(body, LearningMaterialTypes.class);
			lmTypesService.insertLearningMaterialType(lmType);
			return new ResponseEntity<>(lmType, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getLearningMaterialTypeById(@PathVariable("id") String id) {
		LearningMaterialTypes lmType = new LearningMaterialTypes();
		try {
			lmType = lmTypesService.getLearningMaterialTypeById(id);
			return new ResponseEntity<>(lmType, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllLearningMaterialTypes() {
		List<LearningMaterialTypes> lmTypesList = new ArrayList<LearningMaterialTypes>();
		try {
			lmTypesList = lmTypesService.getAllLearningMaterialTypes();
			return new ResponseEntity<>(lmTypesList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping
	public ResponseEntity<?> deleteLearningMaterialTypeById(@RequestParam("id") String id,
			@RequestParam("idUser") String idUser) {
		try {
			lmTypesService.deleteLearningMaterialTypeById(id, idUser);
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
	public ResponseEntity<?> updateLearningMaterialType(@RequestBody String body) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			LearningMaterialTypes lmType = obj.readValue(body, LearningMaterialTypes.class);
			lmTypesService.updateLearningMaterialType(lmType);
			return new ResponseEntity<>(lmType, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
