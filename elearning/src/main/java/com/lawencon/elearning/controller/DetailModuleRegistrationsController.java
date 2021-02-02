package com.lawencon.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.elearning.model.DetailModuleRegistrations;
import com.lawencon.elearning.service.DetailModuleRegistrationsService;

/**
 * @author Nur Alfilail
 */

@RestController
@RequestMapping("detail-module-rgs")
public class DetailModuleRegistrationsController {

	@Autowired
	private DetailModuleRegistrationsService dtlModuleService;

	@GetMapping("{id}")
	public ResponseEntity<?> getDetailModuleRgsById(@PathVariable("id") String id) {
		try {
			DetailModuleRegistrations dtlModuleRgs = dtlModuleService.getDetailModuleRegistrationsById(id);
			return new ResponseEntity<>(dtlModuleRgs, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
