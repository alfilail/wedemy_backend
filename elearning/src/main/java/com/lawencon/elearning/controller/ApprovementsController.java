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
import com.lawencon.elearning.helper.Response;
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
			Response<Approvements> res = new Response<Approvements>(true, HttpStatus.CREATED.toString(), MessageStat.SUCCESS_CREATED.msg, approvement);
			return new ResponseEntity<>(res, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			Response<Approvements> res = new Response<Approvements>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getApprovementById(@PathVariable String id) {
		try {
			Approvements approvement = approvementsService.getApprovementsById(id);
			Response<Approvements> res = new Response<Approvements>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, approvement);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<Approvements> res = new Response<Approvements>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("all")
	public ResponseEntity<?> getAllApprovements() {
		try {
			List<Approvements> approvementsList = approvementsService.getAllApprovements();
			Response<List<Approvements>> res = new Response<List<Approvements>>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, approvementsList);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<List<Approvements>> res = new Response<List<Approvements>>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping
	public ResponseEntity<?> deleteApprovementById(@RequestParam("id") String id,
			@RequestParam("idUser") String idUser) {
		try {
			approvementsService.deleteApprovementById(id, idUser);
			Response<Approvements> res = new Response<Approvements>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_DELETE.msg, null);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<Approvements> res = new Response<Approvements>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping
	public ResponseEntity<?> updateApprovement(@RequestBody String body) {
		try {
			Approvements approvement = new ObjectMapper().readValue(body, Approvements.class);
			approvementsService.updateApprovement(approvement);
			Response<Approvements> res = new Response<Approvements>(true, HttpStatus.CREATED.toString(), MessageStat.SUCCESS_UPDATE.msg, approvement);
			return new ResponseEntity<>(res, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			Response<Approvements> res = new Response<Approvements>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
