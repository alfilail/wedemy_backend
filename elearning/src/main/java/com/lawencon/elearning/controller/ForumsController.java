package com.lawencon.elearning.controller;

import java.util.ArrayList;
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
import com.lawencon.elearning.helper.ForumAndDetailForums;
import com.lawencon.elearning.model.Forums;
import com.lawencon.elearning.service.ForumsService;

@RestController
@RequestMapping("forum")
public class ForumsController {
	
	@Autowired
	private ForumsService forumsService;
	
	@GetMapping("all")
	public ResponseEntity<?> getAllForums() {
		try {
			List<Forums> forum = forumsService.getAllForums();
			return new ResponseEntity<>(forum, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("material/{id}")
	public ResponseEntity<?> getForumByIdDetailModuleRegistration(@PathVariable("id") String id) {
		try {
			List<ForumAndDetailForums> listResult = new ArrayList<>();
			listResult = forumsService.getForumByIdDetailModuleRegistration(id);
			return new ResponseEntity<>(listResult, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getForumById(@PathVariable("id") String id) {
		try {
			Forums forum = forumsService.getForumById(id);
			return new ResponseEntity<>(forum, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> insertForum(@RequestBody String body) {
		try {
			Forums forum = new ObjectMapper().readValue(body, Forums.class);
			forumsService.insertForum(forum);
			return new ResponseEntity<>(forum, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PatchMapping
	public ResponseEntity<?> updateForum(@RequestBody String body) {
		try {
			Forums forum = new ObjectMapper().readValue(body, Forums.class);
			forumsService.updateContentForum(forum);
			return new ResponseEntity<>(forum, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteForumById(@RequestParam("id") String id, 
			@RequestParam("idUser") String idUser) {
		try {
			forumsService.deleteForumById(id, idUser);
			return new ResponseEntity<>("Forum Succesfully Deleted", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
