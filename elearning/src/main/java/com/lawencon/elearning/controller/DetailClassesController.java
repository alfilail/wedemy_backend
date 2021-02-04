package com.lawencon.elearning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.elearning.helper.Response;
import com.lawencon.elearning.model.DetailClasses;
import com.lawencon.elearning.service.DetailClassesService;
import com.lawencon.elearning.util.MessageStat;

@RestController
@RequestMapping("detail-class")
public class DetailClassesController extends ElearningBaseController {

	@Autowired
	private DetailClassesService dtlClassesService;

	@GetMapping("active")
	public ResponseEntity<?> getAllDetailClasses() {
		try {
			List<DetailClasses> dtlClass = dtlClassesService.getAllDetailClass();
			Response<List<DetailClasses>> res = new Response<List<DetailClasses>>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, dtlClass);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<List<DetailClasses>> res = new Response<List<DetailClasses>>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("inactive")
	public ResponseEntity<?> getAllInactiveDetailClasses() {
		try {
			List<DetailClasses> dtlClass = dtlClassesService.getAllInactiveDetailClass();
			Response<List<DetailClasses>> res = new Response<List<DetailClasses>>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, dtlClass);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<List<DetailClasses>> res = new Response<List<DetailClasses>>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getClassById(@PathVariable("id") String id) {
		try {
			DetailClasses dtlClass = dtlClassesService.getDetailClassById(id);
			Response<DetailClasses> res = new Response<DetailClasses>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, dtlClass);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<DetailClasses> res = new Response<DetailClasses>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("tutor/{idTutor}")
	public ResponseEntity<?> getTutorClasses(@PathVariable("idTutor") String idTutor) {
		try {
			List<DetailClasses> listResult = dtlClassesService.getTutorClasses(idTutor);
			Response<List<DetailClasses>> res = new Response<List<DetailClasses>>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, listResult);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<List<DetailClasses>> res = new Response<List<DetailClasses>>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("popular")
	public ResponseEntity<?> getPopularClasses() {
		try {
			List<DetailClasses> listResult = dtlClassesService.getPopularClasses();
			Response<List<DetailClasses>> res = new Response<List<DetailClasses>>(true, HttpStatus.OK.toString(), MessageStat.SUCCESS_RETRIEVE.msg, listResult);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Response<List<DetailClasses>> res = new Response<List<DetailClasses>>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
