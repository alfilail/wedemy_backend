package com.lawencon.elearning.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.lawencon.elearning.helper.Response;
import com.lawencon.elearning.model.Approvements;
import com.lawencon.model.BaseEntity;

public class ElearningBaseController {
	
	protected String getMessage(Exception e) {
		if(e.getMessage() != null && e.getCause() != null && e.getCause().getMessage() != null) {
			return e.getCause().getMessage();
		} else if(e.getMessage() != null) {
			return e.getMessage();
		} else {
			return "Error";
		}
	}
	
//	protected <T extends BaseEntity> ResponseEntity responseData(T data, Exception e) {
//		Response res = new Response<data>(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(), getMessage(e), null);
//		return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
}
