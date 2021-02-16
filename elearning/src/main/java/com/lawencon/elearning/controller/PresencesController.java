package com.lawencon.elearning.controller;

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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lawencon.elearning.model.Presences;
import com.lawencon.elearning.service.PresencesService;
import com.lawencon.elearning.constant.MessageStat;

/**
 * @author Nur Alfilail
 */

@RestController
@RequestMapping("presence")
public class PresencesController extends ElearningBaseController {

	@Autowired
	private PresencesService presencesService;

	@PostMapping
	public ResponseEntity<?> insertPresence(@RequestBody String body) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			Presences presences = obj.readValue(body, Presences.class);
			presencesService.insert(presences);
			return responseSuccess(presences, HttpStatus.OK, MessageStat.SUCCESS_CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getPresenceById(@PathVariable String id) {
		try {
			Presences presence = presencesService.getById(id);
			return responseSuccess(presence, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("all")
	public ResponseEntity<?> getAllPresences() {
		try {
			List<Presences> presencesList = presencesService.getAll();
			return responseSuccess(presencesList, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deletePresenceById(@PathVariable("id") String id) {
		try {
			presencesService.deleteById(id);
			return responseSuccess(null, HttpStatus.OK, MessageStat.SUCCESS_DELETE);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			return new ResponseEntity<>("Data used in another table", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@PutMapping
	public ResponseEntity<?> updatePresence(@RequestBody String body) {
		try {
			Presences presence = new ObjectMapper().readValue(body, Presences.class);
			presencesService.update(presence);
			return responseSuccess(presence, HttpStatus.OK, MessageStat.SUCCESS_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}
}
