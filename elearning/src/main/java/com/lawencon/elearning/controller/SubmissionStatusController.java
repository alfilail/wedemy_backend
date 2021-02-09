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
			SubmissionStatus submissionStatus = new ObjectMapper().readValue(body, SubmissionStatus.class);
			submissionStatusService.insertSubmissionStatus(submissionStatus);
			return responseSuccess(submissionStatus, HttpStatus.OK, MessageStat.SUCCESS_CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getSubmissionStatusById(@PathVariable("id") String id) {
		SubmissionStatus submissionStatus = new SubmissionStatus();
		try {
			submissionStatus = submissionStatusService.getSubmissionStatusById(id);
			return responseSuccess(submissionStatus, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllSubmissionStatus() {
		List<SubmissionStatus> submissionStatusList = new ArrayList<SubmissionStatus>();
		try {
			submissionStatusList = submissionStatusService.getAllSubmissionStatus();
			return responseSuccess(submissionStatusList, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@DeleteMapping
	public ResponseEntity<?> deleteSubmissionStatusById(@RequestParam("id") String id,
			@RequestParam("idUser") String idUser) {
		try {
			submissionStatusService.deleteSubmissionStatusById(id, idUser);
			return responseSuccess(null, HttpStatus.OK, MessageStat.SUCCESS_DELETE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@PutMapping
	public ResponseEntity<?> updateSubmissionStatus(@RequestBody String body) {
		try {
			SubmissionStatus submissionStatus = new ObjectMapper().readValue(body, SubmissionStatus.class);
			submissionStatusService.updateSubmissionStatus(submissionStatus);
			return responseSuccess(submissionStatus, HttpStatus.OK, MessageStat.SUCCESS_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}
}
