package com.lawencon.elearning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lawencon.elearning.constant.MessageStat;
import com.lawencon.elearning.model.DetailForums;
import com.lawencon.elearning.service.DetailForumsService;

@RestController
@RequestMapping("detail-forum")
public class DetailForumsController extends ElearningBaseController {

	@Autowired
	private DetailForumsService detailForumService;

	@PostMapping
	public ResponseEntity<?> insert(@RequestBody String body) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			DetailForums detailForum = obj.readValue(body, DetailForums.class);
			detailForumService.insertDetailForum(detailForum);
			return responseSuccess(detailForum, HttpStatus.OK, MessageStat.SUCCESS_CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping
	public ResponseEntity<?> getAll() {
		try {
			List<DetailForums> detailForums = detailForumService.getAllDetailForums();
			return responseSuccess(detailForums, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable("id") String id) {
		try {
			DetailForums detailForum = detailForumService.getDetailForumById(id);
			return responseSuccess(detailForum, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("forum/{idForum}")
	public ResponseEntity<?> getAllByIdForum(@PathVariable("idForum") String idForum) {
		try {
			List<DetailForums> detailForums = detailForumService.getAllDetailForumsByIdForum(idForum);
			return responseSuccess(detailForums, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody String body) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			DetailForums detailForum = obj.readValue(body, DetailForums.class);
			detailForumService.updateDetailForum(detailForum);
			DetailForums dtlForum = detailForumService.getDetailForumById(detailForum.getId());
			return responseSuccess(dtlForum, HttpStatus.OK, MessageStat.SUCCESS_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

}
