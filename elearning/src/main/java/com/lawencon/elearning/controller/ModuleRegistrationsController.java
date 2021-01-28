package com.lawencon.elearning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.elearning.model.ModuleRegistrations;
import com.lawencon.elearning.service.ModuleRegistrationsService;

/**
 * @author Nur Alfilail
 */

@RestController
@RequestMapping("module-registration")
public class ModuleRegistrationsController {

	@Autowired
	private ModuleRegistrationsService moduleRgsService;

	@GetMapping("iddtlclass/{idClass}")
	public ResponseEntity<?> getModuleById(@PathVariable("idClass") String id) {
		try {
			List<ModuleRegistrations> moduleRgsList = moduleRgsService.getByIdClass(id);
			return new ResponseEntity<>(moduleRgsList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
