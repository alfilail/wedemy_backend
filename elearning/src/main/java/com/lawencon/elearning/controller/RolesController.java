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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.elearning.model.Roles;
import com.lawencon.elearning.service.RolesService;

@RestController
@RequestMapping("role")
public class RolesController {
	@Autowired
	private RolesService rolesService;

	@PostMapping
	public ResponseEntity<?> insertRole(@RequestBody String body) {
		try {
			Roles role= new ObjectMapper().readValue(body, Roles.class);
			rolesService.insertRole(role);
			return new ResponseEntity<>(role, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getRoleById(@PathVariable("id") String id) {
		Roles role = new Roles();
		try {
			role = rolesService.getRoleById(id);
			return new ResponseEntity<>(role, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllRoles() {
		List<Roles> rolesList = new ArrayList<Roles>();
		try {
			rolesList = rolesService.getAllRoles();
			return new ResponseEntity<>(rolesList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteRoleById(@PathVariable("id") String id) {
		try {
			rolesService.deleteRoleById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			return new ResponseEntity<>("Data used in another table", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping
	public ResponseEntity<?> updateRole(@RequestBody String body) {
		try {
			Roles role = new ObjectMapper().readValue(body, Roles.class);
			rolesService.updateRole(role);
			return new ResponseEntity<>(role, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
