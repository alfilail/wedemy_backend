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
import com.lawencon.elearning.helper.Response;
import com.lawencon.elearning.model.Approvements;
import com.lawencon.elearning.service.ApprovementsService;
import com.lawencon.elearning.util.MessageStat;

/**
 * @author Nur Alfilail
 */

@RestController
@RequestMapping("approvement")
public class ApprovementsController {

	@Autowired
	private ApprovementsService approvementsService;

	@PostMapping
	public Response<?> insertApprovement(@RequestBody String body) {
		try {
			Approvements approvement = new ObjectMapper().readValue(body, Approvements.class);
			approvementsService.insertApprovement(approvement);
//			return new ResponseEntity<>(approvement, HttpStatus.CREATED);
			return new Response<>(true, HttpStatus.CREATED, MessageStat.SUCCESS_CREATED, approvement);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(false, HttpStatus.INTERNAL_SERVER_ERROR, MessageStat.FAILED, null);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getApprovementById(@PathVariable String id) {
		try {
			Approvements approvement = approvementsService.getApprovementsById(id);
			return new ResponseEntity<>(approvement, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("all")
	public Response<?> getAllApprovements() {
		try {
			List<Approvements> approvementsList = approvementsService.getAllApprovements();
//			return new Response<>(approvementsList, HttpStatus.CREATED);
			return new Response<>(true, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE, approvementsList);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(false, HttpStatus.INTERNAL_SERVER_ERROR, MessageStat.FAILED, null);
//			return new Response<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping
	public ResponseEntity<?> deleteApprovementById(@RequestParam("id") String id,
			@RequestParam("idUser") String idUser) {
		try {
			approvementsService.deleteApprovementById(id, idUser);
			return new ResponseEntity<>("Approvement berhasil dihapus", HttpStatus.OK);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			return new ResponseEntity<>("Data used in another table", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping
	public ResponseEntity<?> updateApprovement(@RequestBody String body) {
		try {
			Approvements approvement = new ObjectMapper().readValue(body, Approvements.class);
			approvementsService.updateApprovement(approvement);
			return new ResponseEntity<>(approvement, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
