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
import com.lawencon.elearning.helper.Response;
import com.lawencon.elearning.model.Modules;
import com.lawencon.elearning.service.ModulesService;
import com.lawencon.elearning.util.MessageStat;

@RestController
@RequestMapping("module")
public class ModulesController extends ElearningBaseController {
	
	@Autowired
	private ModulesService moduleService;
	
	@GetMapping("all")
	public ResponseEntity<?> getAllModules() {
		List<Modules> listModules = new ArrayList<>();
		try {
			listModules = moduleService.getAllModules();
			return responseSuccess(listModules, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getModuleById(@PathVariable("id") String id) {
		Modules module = new Modules();
		try {
			module = moduleService.getModuleById(id);
			return responseSuccess(module, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> insertModule(@RequestBody String body) {
		try {
			Modules module = new ObjectMapper().readValue(body, Modules.class);
			moduleService.insertModule(module);
			Response<Modules> response = new Response<Modules>(true, HttpStatus.CREATED.toString(), MessageStat.SUCCESS_CREATED.msg, module);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			Response<Modules> response = new Response<Modules>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping
	public ResponseEntity<?> updateModule(@RequestBody String body) {
		try {
			Modules module = new ObjectMapper().readValue(body, Modules.class);
			moduleService.updateModule(module);
			Response<Modules> response = new Response<Modules>(true, HttpStatus.CREATED.toString(), MessageStat.SUCCESS_UPDATE.msg, module);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			Response<Modules> response = new Response<Modules>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping
	public ResponseEntity<?> deleteModuleById(@RequestParam("id") String id,
			@RequestParam("idUser") String idUser) {
		try {
			moduleService.deleteModuleById(id, idUser);
			Response<Modules> response = new Response<Modules>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_DELETE.msg, null);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<Modules> response = new Response<Modules>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
