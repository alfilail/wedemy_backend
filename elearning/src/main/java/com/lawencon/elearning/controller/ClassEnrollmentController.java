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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.elearning.constant.MessageStat;
import com.lawencon.elearning.model.ClassEnrollments;
import com.lawencon.elearning.model.DetailClasses;
import com.lawencon.elearning.service.ClassEnrollmentService;

@RestController
@RequestMapping("class-enrollment")
public class ClassEnrollmentController extends ElearningBaseController {

	@Autowired
	private ClassEnrollmentService classEnrollmentService;

	@PostMapping
	public ResponseEntity<?> insert(@RequestBody String body) {
		try {
			ClassEnrollments classEnrollment = new ObjectMapper().readValue(body, ClassEnrollments.class);
			classEnrollmentService.insertClassEnrollment(classEnrollment);
			return responseSuccess(classEnrollment, HttpStatus.OK, MessageStat.SUCCESS_CREATE_ENROLL);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping
	public ResponseEntity<?> getAll() {
		try {
			List<ClassEnrollments> classEnrollments = classEnrollmentService.getAllClassEnrollments();
			return responseSuccess(classEnrollments, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable("id") String id) {
		try {
			ClassEnrollments classEnrollment = classEnrollmentService.getClassEnrollmentsById(id);
			return responseSuccess(classEnrollment, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("participant/{id}")
	public ResponseEntity<?> getAllByIdParticipant(@PathVariable("id") String id) {
		try {
			List<DetailClasses> classEnrollments = classEnrollmentService.getAllClassEnrollmentsByIdUser(id);
			return responseSuccess(classEnrollments, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("participant/detail-class")
	public ResponseEntity<?> getAllByIdDtlClassAndIdParticipant(@RequestParam("idDtlClass") String idDtlClass,
			@RequestParam("idUser") String idUser) {
		try {
			ClassEnrollments classEnrollment = classEnrollmentService
					.getClassEnrollmentByIdDtlClassAndIdUser(idDtlClass, idUser);
			return responseSuccess(classEnrollment, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody String body) {
		try {
			ClassEnrollments classEnrollment = new ObjectMapper().readValue(body, ClassEnrollments.class);
			classEnrollmentService.updateClassEnrollments(classEnrollment);
			return responseSuccess(classEnrollment, HttpStatus.OK, MessageStat.SUCCESS_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
		try {
			classEnrollmentService.deleteClassEnrollmentsById(id);
			return responseSuccess(null, HttpStatus.OK, MessageStat.SUCCESS_DELETE);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			return new ResponseEntity<>("Data used in another table", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

}
