package com.lawencon.elearning.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lawencon.elearning.model.DetailModuleRegistrations;
import com.lawencon.elearning.model.LearningMaterials;
import com.lawencon.elearning.service.LearningMaterialsService;

/**
 * @author Nur Alfilail
 */

@RestController
@RequestMapping("learning-material")
public class LearningMaterialsController {

	@Autowired
	private LearningMaterialsService learningMaterialsService;

	@PostMapping
	public ResponseEntity<?> insertLearningMaterial(@RequestPart String body, @RequestPart("file") MultipartFile file) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			DetailModuleRegistrations dtlModuleRgs = obj.readValue(body, DetailModuleRegistrations.class);
			learningMaterialsService.insertLearningMaterial(dtlModuleRgs, file);
			return new ResponseEntity<>(dtlModuleRgs, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getLearningMaterialById(@PathVariable String id) {
		try {
			LearningMaterials learningMaterial = learningMaterialsService.getLearningMaterialById(id);
			return new ResponseEntity<>(learningMaterial, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("all")
	public ResponseEntity<?> getAllLearningMaterials() {
		try {
			List<LearningMaterials> learningMaterialsList = learningMaterialsService.getAllLearningMaterials();
			return new ResponseEntity<>(learningMaterialsList, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping
	public ResponseEntity<?> deleteLearningMaterialById(@RequestParam("id") String id,
			@RequestParam("idUser") String idUser) {
		try {
			learningMaterialsService.deleteLearningMaterialById(id, idUser);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			return new ResponseEntity<>("Data used in another table", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping
	public ResponseEntity<?> updateLearningMaterial(@RequestPart String body, @RequestPart("file") MultipartFile file) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			LearningMaterials learningMaterial = obj.readValue(body, LearningMaterials.class);
			learningMaterialsService.updateLearningMaterial(learningMaterial, file);
			return new ResponseEntity<>(learningMaterial, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
