package com.lawencon.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.elearning.model.ApprovementsRenewal;
import com.lawencon.elearning.service.ApprovementsRenewalService;

@RestController
@RequestMapping("approvement-renewal")
public class ApprovementsRenewalController {
	@Autowired
	private ApprovementsRenewalService approvementsRenewalService;
	
	@PostMapping
	public ResponseEntity<?> insertApprovementsRenewal(@RequestBody String body) {
		try {
			ApprovementsRenewal approvementsRenewal = new ObjectMapper().readValue(body, ApprovementsRenewal.class);
			approvementsRenewalService.insertApprovementsRenewal(approvementsRenewal);
			return new ResponseEntity<>(approvementsRenewal, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
