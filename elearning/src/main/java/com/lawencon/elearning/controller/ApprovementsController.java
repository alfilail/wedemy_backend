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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.elearning.model.Approvements;
import com.lawencon.elearning.service.ApprovementsService;
import com.lawencon.elearning.util.MessageStat;

/**
 * @author Nur Alfilail
 */

@RestController
@RequestMapping("approvement")
public class ApprovementsController extends ElearningBaseController {

	@Autowired
	private ApprovementsService approvementsService;

	@PostMapping
	public ResponseEntity<?> insertApprovement(@RequestBody String body) {
		try {
			Approvements approvement = new ObjectMapper().readValue(body, Approvements.class);
			approvementsService.insertApprovement(approvement);
			return responseSuccess(approvement, HttpStatus.OK, MessageStat.SUCCESS_CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getApprovementById(@PathVariable String id) {
		try {
			Approvements approvement = approvementsService.getApprovementsById(id);
			return responseSuccess(approvement, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("all")
	public ResponseEntity<?> getAllApprovements() {
		try {
			List<Approvements> approvementsList = approvementsService.getAllApprovements();
			return responseSuccess(approvementsList, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@DeleteMapping
	public ResponseEntity<?> deleteApprovementById(@RequestParam("id") String id,
			@RequestParam("idUser") String idUser) {
		try {
			approvementsService.deleteApprovementById(id, idUser);
			return responseSuccess(null, HttpStatus.OK, MessageStat.SUCCESS_DELETE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@PutMapping
	public ResponseEntity<?> updateApprovement(@RequestBody String body) {
		try {
			Approvements approvement = new ObjectMapper().readValue(body, Approvements.class);
			approvementsService.updateApprovement(approvement);
			return responseSuccess(approvement, HttpStatus.OK, MessageStat.SUCCESS_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}
}
