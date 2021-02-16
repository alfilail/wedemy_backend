package com.lawencon.elearning.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.elearning.helper.ForumAndDetailForums;
import com.lawencon.elearning.model.Forums;
import com.lawencon.elearning.service.ForumsService;
import com.lawencon.elearning.constant.MessageStat;

@RestController
@RequestMapping("forum")
public class ForumsController extends ElearningBaseController {

	@Autowired
	private ForumsService forumsService;

	@GetMapping("all")
	public ResponseEntity<?> getAllForums() {
		try {
			List<Forums> forum = forumsService.getAllForums();
			return responseSuccess(forum, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("material/{id}")
	public ResponseEntity<?> getForumByIdDetailModuleRegistration(@PathVariable("id") String id) {
		try {
			List<ForumAndDetailForums> listResult = new ArrayList<>();
			listResult = forumsService.getForumByIdDetailModuleRegistration(id);
			return responseSuccess(listResult, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getForumById(@PathVariable("id") String id) {
		try {
			Forums forum = forumsService.getForumById(id);
			return responseSuccess(forum, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@PostMapping
	public ResponseEntity<?> insertForum(@RequestBody String body) {
		try {
			Forums forum = new ObjectMapper().readValue(body, Forums.class);
			forumsService.insertForum(forum);
			return responseSuccess(forum, HttpStatus.OK, MessageStat.SUCCESS_CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@PatchMapping
	public ResponseEntity<?> updateForum(@RequestBody String body) {
		try {
			Forums forum = new ObjectMapper().readValue(body, Forums.class);
			forumsService.updateContentForum(forum);
			return responseSuccess(forum, HttpStatus.OK, MessageStat.SUCCESS_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

}
