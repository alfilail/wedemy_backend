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
import com.lawencon.elearning.model.SubmissionStatus;
import com.lawencon.elearning.service.SubmissionStatusService;

@RestController
@RequestMapping("submission-status")
public class SubmissionStatusController {
	@Autowired
	private SubmissionStatusService submissionStatusService;

	@PostMapping
	public ResponseEntity<?> insertSubmissionStatus(@RequestBody String body) {
		try {
			SubmissionStatus submissionStatus= new ObjectMapper().readValue(body, SubmissionStatus.class);
			submissionStatusService.insertSubmissionStatus(submissionStatus);
			return new ResponseEntity<>(submissionStatus, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getSubmissionStatusById(@PathVariable("id") String id) {
		SubmissionStatus submissionStatus = new SubmissionStatus();
		try {
			submissionStatus = submissionStatusService.getSubmissionStatusById(id);
			return new ResponseEntity<>(submissionStatus, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllSubmissionStatus() {
		List<SubmissionStatus> submissionStatusList = new ArrayList<SubmissionStatus>();
		try {
			submissionStatusList = submissionStatusService.getAllSubmissionStatus();
			return new ResponseEntity<>(submissionStatusList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping
	public ResponseEntity<?> deleteSubmissionStatusById(@RequestParam("id") String id,
			@RequestParam("idUser") String idUser) {
		try {
			submissionStatusService.deleteSubmissionStatusById(id, idUser);
			return new ResponseEntity<>("Status Submission berhasil dihapus" ,HttpStatus.OK);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			return new ResponseEntity<>("Data used in another table", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping
	public ResponseEntity<?> updateSubmissionStatus(@RequestBody String body) {
		try {
			SubmissionStatus submissionStatus = new ObjectMapper().readValue(body, SubmissionStatus.class);
			submissionStatusService.updateSubmissionStatus(submissionStatus);
			return new ResponseEntity<>(submissionStatus, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
