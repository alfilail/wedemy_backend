package com.lawencon.elearning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.elearning.model.DetailClasses;
import com.lawencon.elearning.service.DetailClassesService;

@RestController
@RequestMapping("detail-class")
public class DetailClassesController {
	
	@Autowired
	private DetailClassesService dtlClassesService;
	
	@GetMapping("all")
	public ResponseEntity<?> getAllDetailClasses() {
		try {
			List<DetailClasses> dtlClass = dtlClassesService.getAllDetailClass();
			return new ResponseEntity<>(dtlClass, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
