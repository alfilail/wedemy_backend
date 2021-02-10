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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.elearning.model.Modules;
import com.lawencon.elearning.service.ModulesService;
import com.lawencon.elearning.util.MessageStat;

@RestController
@RequestMapping("module")
public class ModulesController extends ElearningBaseController {

	@Autowired
	private ModulesService moduleService;

	@GetMapping
	public ResponseEntity<?> getAll() {
		List<Modules> listModules = new ArrayList<>();
		try {
			listModules = moduleService.getAll();
			return responseSuccess(listModules, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable("id") String id) {
		Modules module = new Modules();
		try {
			module = moduleService.getById(id);
			return responseSuccess(module, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@PostMapping
	public ResponseEntity<?> insert(@RequestBody String body) {
		try {
			Modules module = new ObjectMapper().readValue(body, Modules.class);
			moduleService.insert(module);
			return responseSuccess(module, HttpStatus.CREATED, MessageStat.SUCCESS_CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody String body) {
		try {
			Modules module = new ObjectMapper().readValue(body, Modules.class);
			moduleService.update(module);
			return responseSuccess(module, HttpStatus.OK, MessageStat.SUCCESS_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@DeleteMapping
	public ResponseEntity<?> deleteById(@RequestParam("id") String id, @RequestParam("idUser") String idUser) {
		try {
			moduleService.deleteById(id, idUser);
			return responseSuccess(null, HttpStatus.OK, MessageStat.SUCCESS_DELETE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}
}
