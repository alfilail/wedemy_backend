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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lawencon.elearning.model.Profiles;
import com.lawencon.elearning.model.Users;
import com.lawencon.elearning.service.UsersService;
import com.lawencon.elearning.util.MessageStat;

@RestController
@RequestMapping("user")
public class UsersController extends ElearningBaseController {

	@Autowired
	private UsersService usersService;

	@GetMapping("all")
	public ResponseEntity<?> getAllUsers() {
		try {
			List<Users> listUsers = usersService.getAllUsers();
			return responseSuccess(listUsers, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
		try {
			Users user = usersService.getUserById(id);
			return responseSuccess(user, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("role/{roleCode}")
	public ResponseEntity<?> getUsersByRoleCode(@PathVariable("roleCode") String roleCode) {
		try {
			List<Users> user = usersService.getUsersByRoleCode(roleCode);
			return responseSuccess(user, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@PostMapping
	public ResponseEntity<?> insertUser(@RequestBody String body) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			Users user = obj.readValue(body, Users.class);
			usersService.insertUser(user);
			return responseSuccess(user, HttpStatus.OK, MessageStat.SUCCESS_CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@PatchMapping("/forget-password")
	public ResponseEntity<?> resetPassword(@RequestBody String body) {
		try {
			Profiles profile = new ObjectMapper().readValue(body, Profiles.class);
			Users user = usersService.updateUserPassword(profile);
			return responseSuccess(user, HttpStatus.OK, MessageStat.SUCCESS_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@PatchMapping
	public ResponseEntity<?> updateUser(@RequestBody String body) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			Users user = obj.readValue(body, Users.class);
			usersService.updateUser(user);
			return responseSuccess(user, HttpStatus.OK, MessageStat.SUCCESS_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@DeleteMapping
	public ResponseEntity<?> deleteUserById(@RequestParam("id") String id, @RequestParam("idUser") String idUser) {
		try {
			usersService.deleteUserById(id, idUser);
			return responseSuccess(null, HttpStatus.OK, MessageStat.SUCCESS_DELETE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

}