package com.lawencon.elearning.controller;

import java.util.List;

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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lawencon.elearning.helper.DetailClassInformation;
import com.lawencon.elearning.model.DetailClasses;
import com.lawencon.elearning.service.DetailClassesService;
import com.lawencon.elearning.util.MessageStat;

@RestController
@RequestMapping("detail-class")
public class DetailClassesController extends ElearningBaseController {

	@Autowired
	private DetailClassesService dtlClassesService;

	@PostMapping
	public ResponseEntity<?> ReactiveClass(@RequestBody String body) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			DetailClasses detailClass = obj.readValue(body, DetailClasses.class);
			dtlClassesService.reactiveOldClass(detailClass);
			return responseSuccess(detailClass, HttpStatus.OK, MessageStat.SUCCESS_CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("active")
	public ResponseEntity<?> getAllDetailClasses() {
		try {
			List<DetailClasses> dtlClass = dtlClassesService.getAllDetailClass();
			return responseSuccess(dtlClass, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("inactive")
	public ResponseEntity<?> getAllInactiveDetailClasses() {
		try {
			List<DetailClasses> dtlClass = dtlClassesService.getAllInactiveDetailClass();
			return responseSuccess(dtlClass, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getClassById(@PathVariable("id") String id) {
		try {
			DetailClasses dtlClass = dtlClassesService.getDetailClassById(id);
			return responseSuccess(dtlClass, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("information/{id}")
	public ResponseEntity<?> getDetailClassInformationById(@PathVariable("id") String id) {
		try {
			DetailClassInformation dtlClassInformation = dtlClassesService.getByIdDtlClass(id);
			return responseSuccess(dtlClassInformation, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("tutor/{idTutor}")
	public ResponseEntity<?> getTutorClasses(@PathVariable("idTutor") String idTutor) {
		try {
			List<DetailClasses> listResult = dtlClassesService.getTutorClasses(idTutor);
			return responseSuccess(listResult, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("popular")
	public ResponseEntity<?> getPopularClasses() {
		try {
			List<DetailClasses> listResult = dtlClassesService.getPopularClasses();
			return responseSuccess(listResult, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

}
