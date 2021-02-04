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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lawencon.elearning.helper.Response;
import com.lawencon.elearning.model.DetailModuleRegistrations;
import com.lawencon.elearning.model.LearningMaterials;
import com.lawencon.elearning.service.LearningMaterialsService;
import com.lawencon.elearning.util.MessageStat;

/**
 * @author Nur Alfilail
 */

@RestController
@RequestMapping("learning-material")
public class LearningMaterialsController extends ElearningBaseController {

	@Autowired
	private LearningMaterialsService learningMaterialsService;

	@PostMapping
	public ResponseEntity<?> insertLearningMaterial(@RequestPart String body, @RequestPart("file") MultipartFile file) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			DetailModuleRegistrations dtlModuleRgs = obj.readValue(body, DetailModuleRegistrations.class);
			learningMaterialsService.insertLearningMaterial(dtlModuleRgs, file);
			Response<DetailModuleRegistrations> res = new Response<DetailModuleRegistrations>(true, HttpStatus.CREATED.toString(), MessageStat.SUCCESS_CREATED.msg, dtlModuleRgs);
			return new ResponseEntity<>(res, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			Response<DetailModuleRegistrations> res = new Response<DetailModuleRegistrations>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getLearningMaterialById(@PathVariable String id) {
		try {
			LearningMaterials learningMaterial = learningMaterialsService.getLearningMaterialById(id);
			Response<LearningMaterials> res = new Response<LearningMaterials>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, learningMaterial);
			return new ResponseEntity<>(res, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			Response<LearningMaterials> res = new Response<LearningMaterials>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("all")
	public ResponseEntity<?> getAllLearningMaterials() {
		try {
			List<LearningMaterials> learningMaterialsList = learningMaterialsService.getAllLearningMaterials();
			Response<List<LearningMaterials>> res = new Response<List<LearningMaterials>>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, learningMaterialsList);
			return new ResponseEntity<>(res, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			Response<List<LearningMaterials>> res = new Response<List<LearningMaterials>>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping
	public ResponseEntity<?> deleteLearningMaterialById(@RequestParam("id") String id,
			@RequestParam("idUser") String idUser) {
		try {
			learningMaterialsService.deleteLearningMaterialById(id, idUser);
			Response<LearningMaterials> res = new Response<LearningMaterials>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_DELETE.msg, null);
			return new ResponseEntity<>(res, HttpStatus.OK);
		}  catch (Exception e) {
			e.printStackTrace();
			Response<LearningMaterials> res = new Response<LearningMaterials>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping
	public ResponseEntity<?> updateLearningMaterial(@RequestPart String body, @RequestPart("file") MultipartFile file) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			LearningMaterials learningMaterial = obj.readValue(body, LearningMaterials.class);
			learningMaterialsService.updateLearningMaterial(learningMaterial, file);
			Response<LearningMaterials> res = new Response<LearningMaterials>(true, HttpStatus.CREATED.toString(), MessageStat.SUCCESS_UPDATE.msg, learningMaterial);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<LearningMaterials> res = new Response<LearningMaterials>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
