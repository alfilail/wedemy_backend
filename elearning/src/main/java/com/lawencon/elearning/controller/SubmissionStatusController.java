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
import com.lawencon.elearning.helper.Response;
import com.lawencon.elearning.model.SubmissionStatus;
import com.lawencon.elearning.service.SubmissionStatusService;
import com.lawencon.elearning.util.MessageStat;

@RestController
@RequestMapping("submission-status")
public class SubmissionStatusController extends ElearningBaseController {
	@Autowired
	private SubmissionStatusService submissionStatusService;

	@PostMapping
	public ResponseEntity<?> insertSubmissionStatus(@RequestBody String body) {
		try {
			SubmissionStatus submissionStatus= new ObjectMapper().readValue(body, SubmissionStatus.class);
			submissionStatusService.insertSubmissionStatus(submissionStatus);
			Response<SubmissionStatus> response = new Response<SubmissionStatus>(true, HttpStatus.CREATED.toString(), MessageStat.SUCCESS_CREATED.msg, submissionStatus);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			Response<SubmissionStatus> response = new Response<SubmissionStatus>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getSubmissionStatusById(@PathVariable("id") String id) {
		SubmissionStatus submissionStatus = new SubmissionStatus();
		try {
			submissionStatus = submissionStatusService.getSubmissionStatusById(id);
			Response<SubmissionStatus> response = new Response<SubmissionStatus>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, submissionStatus);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<SubmissionStatus> response = new Response<SubmissionStatus>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllSubmissionStatus() {
		List<SubmissionStatus> submissionStatusList = new ArrayList<SubmissionStatus>();
		try {
			submissionStatusList = submissionStatusService.getAllSubmissionStatus();
			Response<List<SubmissionStatus>> response = new Response<List<SubmissionStatus>>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, submissionStatusList);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<List<SubmissionStatus>> response = new Response<List<SubmissionStatus>>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), submissionStatusList);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping
	public ResponseEntity<?> deleteSubmissionStatusById(@RequestParam("id") String id,
			@RequestParam("idUser") String idUser) {
		try {
			submissionStatusService.deleteSubmissionStatusById(id, idUser);
			Response<SubmissionStatus> response = new Response<SubmissionStatus>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_DELETE.msg, null);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<SubmissionStatus> response = new Response<SubmissionStatus>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping
	public ResponseEntity<?> updateSubmissionStatus(@RequestBody String body) {
		try {
			SubmissionStatus submissionStatus = new ObjectMapper().readValue(body, SubmissionStatus.class);
			submissionStatusService.updateSubmissionStatus(submissionStatus);
			Response<SubmissionStatus> response = new Response<SubmissionStatus>(true, HttpStatus.CREATED.toString(), MessageStat.SUCCESS_UPDATE.msg, submissionStatus);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			Response<SubmissionStatus> response = new Response<SubmissionStatus>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
