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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lawencon.elearning.model.Profiles;
import com.lawencon.elearning.service.ProfilesService;

@RestController
@RequestMapping("profile")
public class ProfilesController {
	
	@Autowired
	private ProfilesService profilesService;
	
	@GetMapping("all")
	public ResponseEntity<?> getAllProfiles() {
		try {
			List<Profiles> profile = profilesService.getAllProfiles();
			return new ResponseEntity<>(profile, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getProfileById(@PathVariable("id") String id) {
		try {
			Profiles profile = profilesService.getProfileById(id);
			return new ResponseEntity<>(profile, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> insertProfile(@RequestBody String body) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			Profiles profile = obj.readValue(body, Profiles.class);
			profilesService.insertProfile(profile);
			return new ResponseEntity<>(profile, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PatchMapping
	public ResponseEntity<?> updateProfile(@RequestBody String body) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			Profiles profile = obj.readValue(body, Profiles.class);
			profilesService.updateProfile(profile);
			return new ResponseEntity<>(profile, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteProfileById(@PathVariable("id") String id) {
		try {
			profilesService.deleteProfileById(id);
			return new ResponseEntity<>("Profile Successfully Deleted!", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
