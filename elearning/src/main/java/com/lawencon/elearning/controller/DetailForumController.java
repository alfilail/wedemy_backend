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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lawencon.elearning.model.DetailForums;
import com.lawencon.elearning.service.DetailForumsService;

@RestController
@RequestMapping("detail-forum")
public class DetailForumController {
	@Autowired
	private DetailForumsService detailForumService;
	
	@GetMapping("all")
	public ResponseEntity<?> getAllDetailForum() {
		try {
			List<DetailForums> detailForums = detailForumService.getAllDetailForums();
			return new ResponseEntity<>(detailForums, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getDetailForumById(@PathVariable("id") String id) {
		try {
			DetailForums detailForum = detailForumService.getDetailForumById(id);
			return new ResponseEntity<>(detailForum, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> insertDetailForum(@RequestBody String body) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			DetailForums detailForum = obj.readValue(body, DetailForums.class);
			detailForumService.insertDetailForum(detailForum);
			return new ResponseEntity<>(detailForum, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping
	public ResponseEntity<?> updateDetailForum(@RequestBody String body) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			DetailForums detailForum = obj.readValue(body, DetailForums.class);
			detailForumService.updateDetailForum(detailForum);
			return new ResponseEntity<>(detailForum, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteDetailForumById(@PathVariable("id") String id) {
		try {
			detailForumService.deleteDetailForumById(id);
			return new ResponseEntity<>("Detail Forum Succesfully Deleted", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("forum/{idForum}")
	public ResponseEntity<?> getDetailForumByIdForum(@PathVariable("idForum") String idForum) {
		try {
			List<DetailForums> detailForum = detailForumService.getAllDetailForumsByIdForum(idForum);
			return new ResponseEntity<>(detailForum, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
