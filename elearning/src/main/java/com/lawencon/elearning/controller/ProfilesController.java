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
import com.lawencon.elearning.helper.Response;
import com.lawencon.elearning.model.Profiles;
import com.lawencon.elearning.service.ProfilesService;
import com.lawencon.elearning.util.MessageStat;

@RestController
@RequestMapping("profile")
public class ProfilesController extends ElearningBaseController {
	
	@Autowired
	private ProfilesService profilesService;
	
	@GetMapping("all")
	public ResponseEntity<?> getAllProfiles() {
		try {
			List<Profiles> listProfiles = profilesService.getAllProfiles();
			Response<List<Profiles>> response = new Response<List<Profiles>>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_CREATED.msg, listProfiles);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<List<Profiles>> response = new Response<List<Profiles>>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getProfileById(@PathVariable("id") String id) {
		try {
			Profiles profile = profilesService.getProfileById(id);
			Response<Profiles> response = new Response<Profiles>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, profile);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<Profiles> response = new Response<Profiles>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> insertProfile(@RequestBody String body) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			Profiles profile = obj.readValue(body, Profiles.class);
			profilesService.insertProfile(profile);
			Response<Profiles> response = new Response<Profiles>(true, HttpStatus.CREATED.toString(), MessageStat.SUCCESS_CREATED.msg, profile);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			Response<Profiles> response = new Response<Profiles>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PatchMapping
	public ResponseEntity<?> updateProfile(@RequestBody String body) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			Profiles profile = obj.readValue(body, Profiles.class);
			profilesService.updateProfile(profile);
			Response<Profiles> response = new Response<Profiles>(true, HttpStatus.CREATED.toString(), MessageStat.SUCCESS_UPDATE.msg, profile);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			Response<Profiles> response = new Response<Profiles>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteProfileById(@PathVariable("id") String id) {
		try {
			profilesService.deleteProfileById(id);
			Response<Profiles> response = new Response<Profiles>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_DELETE.msg, null);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<Profiles> response = new Response<Profiles>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
