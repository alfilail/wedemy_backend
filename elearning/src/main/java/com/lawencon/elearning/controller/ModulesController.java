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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.elearning.model.Modules;
import com.lawencon.elearning.service.ModulesService;

@RestController
@RequestMapping("module")
public class ModulesController {
	
	@Autowired
	private ModulesService moduleService;
	
	@GetMapping("all")
	public ResponseEntity<?> getAllModules() {
		try {
			List<Modules> module = moduleService.getAllModules();
			return new ResponseEntity<>(module, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getModuleById(@PathVariable("id") String id) {
		try {
			Modules module = moduleService.getModuleById(id);
			return new ResponseEntity<>(module, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> insertModule(@RequestBody String body) {
		try {
			Modules module = new ObjectMapper().readValue(body, Modules.class);
			moduleService.insertModule(module);
			return new ResponseEntity<>(module, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping
	public ResponseEntity<?> updateModule(@RequestBody String body) {
		try {
			Modules module = new ObjectMapper().readValue(body, Modules.class);
			moduleService.updateModule(module);
			return new ResponseEntity<>(module, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteModuleById(@PathVariable("id") String id) {
		try {
			moduleService.deleteModuleById(id);
			return new ResponseEntity<>("Module Successfully Deleted!", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
