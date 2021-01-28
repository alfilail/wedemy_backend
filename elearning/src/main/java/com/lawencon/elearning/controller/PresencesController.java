package com.lawencon.elearning.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lawencon.elearning.model.Presences;
import com.lawencon.elearning.service.PresencesService;
import com.lawencon.util.JasperUtil;

/**
 * @author Nur Alfilail
 */

@RestController
@RequestMapping("presence")
public class PresencesController {

	@Autowired
	private PresencesService presencesService;

	@PostMapping
	public ResponseEntity<?> insertPresence(@RequestBody String body) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			Presences presence = obj.readValue(body, Presences.class);
			presencesService.insertPresence(presence);
			return new ResponseEntity<>(presence, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getPresenceById(@PathVariable String id) {
		try {
			Presences presence = presencesService.getPresenceById(id);
			return new ResponseEntity<>(presence, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("all")
	public ResponseEntity<?> getAllPresences() {
		try {
			List<Presences> presencesList = presencesService.getAllPresences();
			return new ResponseEntity<>(presencesList, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("report")
	public HttpEntity<?> reportPresence(@RequestParam String idClass, @RequestParam String scheduleDateStart, 
			@RequestParam String scheduleDateEnd) {
		List<?> listData = new ArrayList<>();
		byte[] out;
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate scheduleDateStartNew = LocalDate.parse(scheduleDateStart, dtf);
			LocalDate scheduleDateEndNew = LocalDate.parse(scheduleDateEnd, dtf);
			listData = presencesService.getPresenceReport(idClass, scheduleDateStartNew, scheduleDateEndNew);
			out = JasperUtil.responseToByteArray(listData, "ReportPresence", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		return new HttpEntity<>(out, headers);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deletePresenceById(@PathVariable("id") String id) {
		try {
			presencesService.deletePresenceById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			return new ResponseEntity<>("Data used in another table", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping
	public ResponseEntity<?> updatePresence(@RequestBody String body) {
		try {
			Presences presence = new ObjectMapper().readValue(body, Presences.class);
			presencesService.updatePresence(presence);
			return new ResponseEntity<>(presence, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
