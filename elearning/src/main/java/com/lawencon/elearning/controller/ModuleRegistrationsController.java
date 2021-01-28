package com.lawencon.elearning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.elearning.helper.ModuleAndLearningMaterials;
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

	@GetMapping("dtl-class/{id}")
	public ResponseEntity<?> getModuleByIdDtlClass(@PathVariable("id") String id) {
		try {
			List<ModuleRegistrations> moduleRgsList = moduleRgsService.getByIdDtlClass(id);
			return new ResponseEntity<>(moduleRgsList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("module-and-materials/{id}")
	public ResponseEntity<?> getModuleAndLearningMaterialsByIdDtlClass(@PathVariable("id") String id) {
		try {
			List<ModuleAndLearningMaterials> listResult = moduleRgsService
					.getModuleAndLearningMaterialsByIdDtlClass(id);
			return new ResponseEntity<>(listResult, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
