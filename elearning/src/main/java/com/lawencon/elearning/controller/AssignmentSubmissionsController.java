package com.lawencon.elearning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lawencon.elearning.model.AssignmentSubmissions;
import com.lawencon.elearning.service.AssignmentSubmissionsService;
import com.lawencon.elearning.util.MessageStat;

@RestController
@RequestMapping("assignment-submission")
public class AssignmentSubmissionsController extends ElearningBaseController {

	@Autowired
	private AssignmentSubmissionsService assignmentSubmissionsService;

	@GetMapping("all")
	public ResponseEntity<?> getAllClasses() {
		try {
			List<AssignmentSubmissions> assignmentSubmissions = assignmentSubmissionsService
					.getAllAssignmentSubmissions();
			return responseSuccess(assignmentSubmissions, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getassignmentSubmissionsById(@PathVariable("id") String id) {
		try {
			AssignmentSubmissions assignmentSubmissions = assignmentSubmissionsService.getAssignmentSubmissionsById(id);
			return responseSuccess(assignmentSubmissions, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("participant")
	public ResponseEntity<?> getByIdDtlModuleRgsAndIdParticipant(@RequestParam("idDtlModuleRgs") String idDtlModuleRgs,
			@RequestParam("idParticipant") String idParticipant) {
		try {
			AssignmentSubmissions assignmentSubmissions = assignmentSubmissionsService
					.getByIdDtlModuleRgsAndIdParticipant(idDtlModuleRgs, idParticipant);
			return responseSuccess(assignmentSubmissions, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@PatchMapping
	public ResponseEntity<?> updateAssignmentSubmission(@RequestPart String body,
			@RequestPart("file") MultipartFile file) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			AssignmentSubmissions assignmentSubmissions = obj.readValue(body, AssignmentSubmissions.class);
			assignmentSubmissionsService.updateAssignmentSubmission(assignmentSubmissions, file);
			return responseSuccess(assignmentSubmissions, HttpStatus.OK, MessageStat.SUCCESS_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
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
			return responseSuccess(assignmentSubmissions, HttpStatus.OK, MessageStat.SUCCESS_CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteAssignmentSubmissionById(@PathVariable("id") String id) {
		try {
			assignmentSubmissionsService.deleteAssignmentSubmissionsById(id);
			return responseSuccess(null, HttpStatus.OK, MessageStat.SUCCESS_DELETE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}
}
