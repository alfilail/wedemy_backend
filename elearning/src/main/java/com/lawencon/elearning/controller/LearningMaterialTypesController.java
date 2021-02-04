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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lawencon.elearning.helper.Response;
import com.lawencon.elearning.model.LearningMaterialTypes;
import com.lawencon.elearning.service.LearningMaterialTypesService;
import com.lawencon.elearning.util.MessageStat;

@RestController
@RequestMapping("learning-material-type")
public class LearningMaterialTypesController extends ElearningBaseController {
	@Autowired
	private LearningMaterialTypesService lmTypesService;

	@PostMapping
	public ResponseEntity<?> insertLearningMaterialType(@RequestBody String body) {
		try {
			LearningMaterialTypes lmType = new ObjectMapper().readValue(body, LearningMaterialTypes.class);
			lmTypesService.insertLearningMaterialType(lmType);
			Response<LearningMaterialTypes> res = new Response<LearningMaterialTypes>(true, HttpStatus.CREATED.toString(), MessageStat.SUCCESS_CREATED.msg, lmType);
			return new ResponseEntity<>(res, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			Response<LearningMaterialTypes> res = new Response<LearningMaterialTypes>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getLearningMaterialTypeById(@PathVariable("id") String id) {
		LearningMaterialTypes lmType = new LearningMaterialTypes();
		try {
			lmType = lmTypesService.getLearningMaterialTypeById(id);
			Response<LearningMaterialTypes> res = new Response<LearningMaterialTypes>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, lmType);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<LearningMaterialTypes> res = new Response<LearningMaterialTypes>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllLearningMaterialTypes() {
		List<LearningMaterialTypes> lmTypesList = new ArrayList<LearningMaterialTypes>();
		try {
			lmTypesList = lmTypesService.getAllLearningMaterialTypes();
			Response<List<LearningMaterialTypes>> res = new Response<List<LearningMaterialTypes>>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, lmTypesList);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<List<LearningMaterialTypes>> res = new Response<List<LearningMaterialTypes>>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping
	public ResponseEntity<?> deleteLearningMaterialTypeById(@RequestParam("id") String id,
			@RequestParam("idUser") String idUser) {
		try {
			lmTypesService.deleteLearningMaterialTypeById(id, idUser);
			Response<LearningMaterialTypes> res = new Response<LearningMaterialTypes>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_DELETE.msg, null);
			return new ResponseEntity<>(res ,HttpStatus.OK);
		} 
		catch (Exception e) {
			e.printStackTrace();
			Response<LearningMaterialTypes> res = new Response<LearningMaterialTypes>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping
	public ResponseEntity<?> updateLearningMaterialType(@RequestBody String body) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			LearningMaterialTypes lmType = obj.readValue(body, LearningMaterialTypes.class);
			lmTypesService.updateLearningMaterialType(lmType);
			Response<LearningMaterialTypes> res = new Response<LearningMaterialTypes>(true, HttpStatus.CREATED.toString(), MessageStat.SUCCESS_UPDATE.msg, lmType);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<LearningMaterialTypes> res = new Response<LearningMaterialTypes>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
