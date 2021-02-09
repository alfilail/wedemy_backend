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
import com.lawencon.elearning.model.DetailForums;
import com.lawencon.elearning.service.DetailForumsService;
import com.lawencon.elearning.util.MessageStat;

@RestController
@RequestMapping("detail-forum")
public class DetailForumsController extends ElearningBaseController {
	@Autowired
	private DetailForumsService detailForumService;

	@GetMapping("all")
	public ResponseEntity<?> getAllDetailForum() {
		try {
			List<DetailForums> detailForums = detailForumService.getAllDetailForums();
			return responseSuccess(detailForums, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getDetailForumById(@PathVariable("id") String id) {
		try {
			DetailForums detailForum = detailForumService.getDetailForumById(id);
			return responseSuccess(detailForum, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@PostMapping
	public ResponseEntity<?> insertDetailForum(@RequestBody String body) {
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

	@PutMapping
	public ResponseEntity<?> updateDetailForum(@RequestBody String body) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			DetailForums detailForum = obj.readValue(body, DetailForums.class);
			detailForumService.updateDetailForum(detailForum);
			return responseSuccess(detailForum, HttpStatus.OK, MessageStat.SUCCESS_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("forum/{idForum}")
	public ResponseEntity<?> getDetailForumByIdForum(@PathVariable("idForum") String idForum) {
		try {
			List<DetailForums> detailForum = detailForumService.getAllDetailForumsByIdForum(idForum);
			return responseSuccess(detailForum, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

}
