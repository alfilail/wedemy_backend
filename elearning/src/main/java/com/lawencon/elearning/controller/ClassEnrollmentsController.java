package com.lawencon.elearning.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.elearning.service.ClassEnrollmentService;
import com.lawencon.util.JasperUtil;

@RestController
@RequestMapping("class-enrollment")
public class ClassEnrollmentsController {
	@Autowired
	private ClassEnrollmentService classEnrollmentService;
	
	@GetMapping("certificate")
	public HttpEntity<?> reportCertificate(@RequestParam String idUser, 
			@RequestParam String idClass) {
		List<?> data = new ArrayList<>();
		byte[] out;
		try {
			data = classEnrollmentService.getCertificate(idUser, idClass);
			out = JasperUtil.responseToByteArray(data, "Certificate", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		return new HttpEntity<>(out, headers);
	}
}
