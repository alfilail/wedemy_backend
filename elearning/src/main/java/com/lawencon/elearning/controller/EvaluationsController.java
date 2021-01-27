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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.elearning.model.Evaluations;
import com.lawencon.elearning.service.EvaluationsService;
import com.lawencon.util.JasperUtil;

/**
 * @author Nur Alfilail
 */

@RestController
@RequestMapping("evaluation")
public class EvaluationsController {

	@Autowired
	private EvaluationsService evaluationsService;

	@GetMapping("all")
	public ResponseEntity<?> getAllEvaluations() {
		try {
			List<Evaluations> evaluations = evaluationsService.getAllEvaluations();
			return new ResponseEntity<>(evaluations, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/report/score/all")
	public HttpEntity<?> reportAllScores() {
		List<?> listData = new ArrayList<>();
		byte[] out;
		try {
			listData = evaluationsService.reportAllScore();
			out = JasperUtil.responseToByteArray(listData, "ScoreReport", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		return new HttpEntity<>(out, headers);
	}
	
	@GetMapping("/report/score")
	public HttpEntity<?> reportScore(@RequestParam("idDtlClass") String idDtlClass,
			@RequestParam("idParticipant") String idParticipant) {
		List<?> listData = new ArrayList<>();
		byte[] out;
		try {
			listData = evaluationsService.reportScore(idDtlClass, idParticipant);
			out = JasperUtil.responseToByteArray(listData, "ScoreReport", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		return new HttpEntity<>(out, headers);
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getEvaluationById(@PathVariable("id") String id) {
		try {
			Evaluations evaluation = evaluationsService.getEvaluationById(id);
			return new ResponseEntity<>(evaluation, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<?> insertEvaluation(@RequestPart String body) {
		try {
			ObjectMapper obj = new ObjectMapper();
			Evaluations evaluation = obj.readValue(body, Evaluations.class);
			evaluationsService.insertEvaluation(evaluation);
			return new ResponseEntity<>(evaluation, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
