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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.elearning.helper.Response;
import com.lawencon.elearning.model.Roles;
import com.lawencon.elearning.service.RolesService;
import com.lawencon.elearning.util.MessageStat;

@RestController
@RequestMapping("role")
public class RolesController extends ElearningBaseController {
	@Autowired
	private RolesService rolesService;

	@PostMapping
	public ResponseEntity<?> insertRole(@RequestBody String body) {
		try {
			Roles role = new ObjectMapper().readValue(body, Roles.class);
			rolesService.insertRole(role);
			Response<Roles> response = new Response<Roles>(true, HttpStatus.CREATED.toString(), MessageStat.SUCCESS_CREATED.msg, role);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			Response<Roles> response = new Response<Roles>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getRoleById(@PathVariable("id") String id) {
		Roles role = new Roles();
		try {
			role = rolesService.getRoleById(id);
			Response<Roles> response = new Response<Roles>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, role);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<Roles> response = new Response<Roles>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllRoles() {
		List<Roles> rolesList = new ArrayList<Roles>();
		try {
			rolesList = rolesService.getAllRoles();
			Response<List<Roles>> response = new Response<List<Roles>>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, rolesList);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<List<Roles>> response = new Response<List<Roles>>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteRoleById(@PathVariable("id") String id) {
		try {
			rolesService.deleteRoleById(id);
			Response<Roles> response = new Response<Roles>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_DELETE.msg, null);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<Roles> response = new Response<Roles>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping
	public ResponseEntity<?> updateRole(@RequestBody String body) {
		try {
			Roles role = new ObjectMapper().readValue(body, Roles.class);
			rolesService.updateRole(role);
			Response<Roles> response = new Response<Roles>(true, HttpStatus.CREATED.toString(), MessageStat.SUCCESS_UPDATE.msg, role);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			Response<Roles> response = new Response<Roles>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
