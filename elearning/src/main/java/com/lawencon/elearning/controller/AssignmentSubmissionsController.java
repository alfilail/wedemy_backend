package com.lawencon.elearning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lawencon.elearning.helper.Response;
import com.lawencon.elearning.model.AssignmentSubmissions;
import com.lawencon.elearning.service.AssignmentSubmissionsService;
import com.lawencon.elearning.util.MessageStat;

@RestController
@RequestMapping("assignment-submission")
public class AssignmentSubmissionsController extends ElearningBaseController{

	@Autowired
	private AssignmentSubmissionsService assignmentSubmissionsService;

	@GetMapping("all")
	public ResponseEntity<?> getAllClasses() {
		try {
			List<AssignmentSubmissions> assignmentSubmissions = assignmentSubmissionsService
					.getAllAssignmentSubmissions();
			Response<List<AssignmentSubmissions>> res = 
					new Response<List<AssignmentSubmissions>>
			(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, assignmentSubmissions);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<List<AssignmentSubmissions>> res = 
					new Response<List<AssignmentSubmissions>>
			(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getassignmentSubmissionsById(@PathVariable("id") String id) {
		try {
			AssignmentSubmissions assignmentSubmissions = assignmentSubmissionsService.getAssignmentSubmissionsById(id);
			Response<AssignmentSubmissions> res = new Response<AssignmentSubmissions>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, assignmentSubmissions);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<AssignmentSubmissions> res = new Response<AssignmentSubmissions>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<?> insertAssignmentSubmission(@RequestPart String body,
			@RequestPart("file") MultipartFile file) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			AssignmentSubmissions assignmentSubmissions = obj.readValue(body, AssignmentSubmissions.class);
			assignmentSubmissionsService.insertAssignmentSubmissions(assignmentSubmissions, file);
			Response<AssignmentSubmissions> res = new Response<AssignmentSubmissions>(true, HttpStatus.CREATED.toString(), MessageStat.SUCCESS_CREATED.msg, assignmentSubmissions);
			return new ResponseEntity<>(res, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			Response<AssignmentSubmissions> res = new Response<AssignmentSubmissions>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteAssignmentSubmissionById(@PathVariable("id") String id) {
		try {
			assignmentSubmissionsService.deleteAssignmentSubmissionsById(id);
			Response<AssignmentSubmissions> res = new Response<AssignmentSubmissions>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_DELETE.msg, null);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<AssignmentSubmissions> res = new Response<AssignmentSubmissions>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
