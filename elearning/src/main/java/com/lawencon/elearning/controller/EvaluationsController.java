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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.elearning.helper.ScoreInputs;
import com.lawencon.elearning.model.Evaluations;
import com.lawencon.elearning.service.EvaluationsService;
import com.lawencon.elearning.util.MessageStat;
import com.lawencon.util.JasperUtil;

/**
 * @author Nur Alfilail
 */

@RestController
@RequestMapping("evaluation")
public class EvaluationsController extends ElearningBaseController {

	@Autowired
	private EvaluationsService evaluationsService;

	@GetMapping("all")
	public ResponseEntity<?> getAllEvaluations() {
		try {
			List<Evaluations> evaluations = evaluationsService.getAllEvaluations();
			return responseSuccess(evaluations, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("score-submission")
	public ResponseEntity<?> getByIdDtlClassAndIdDtlModuleRgs(@RequestParam("idDtlClass") String idDtlClass,
			@RequestParam("idDtlModuleRgs") String idDtlModuleRgs) {
		try {
			List<Evaluations> evaluations = evaluationsService.getAllByIdDtlClassAndIdDtlModuleRgs(idDtlClass,
					idDtlModuleRgs);
			return responseSuccess(evaluations, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("/report/scores/{idDtlClass}")
	public HttpEntity<?> reportAllScores(@PathVariable("idDtlClass") String idDtlClass) {
		List<?> listData = new ArrayList<>();
		byte[] out;
		try {
			listData = evaluationsService.reportAllScore(idDtlClass);
			out = JasperUtil.responseToByteArray(listData, "ScoresReport", null);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		return new HttpEntity<>(out, headers);
	}

	@GetMapping("scores/{idDtlClass}")
	public ResponseEntity<?> getScores(@PathVariable("idDtlClass") String idDtlClass) {
		try {
			List<?> scoreList = evaluationsService.reportAllScore(idDtlClass);
			return responseSuccess(scoreList, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
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
			return responseError(e);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		return new HttpEntity<>(out, headers);
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getEvaluationById(@PathVariable("id") String id) {
		try {
			Evaluations evaluation = evaluationsService.getEvaluationById(id);
			return responseSuccess(evaluation, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@PostMapping
	public ResponseEntity<?> insertEvaluation(@RequestBody String body) {
		try {
			ObjectMapper obj = new ObjectMapper();
			ScoreInputs scores = obj.readValue(body, ScoreInputs.class);
			evaluationsService.insertEvaluation(scores);
			return responseSuccess(scores, HttpStatus.OK, MessageStat.SUCCESS_CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody String body) {
		try {
			ObjectMapper obj = new ObjectMapper();
			ScoreInputs scores = obj.readValue(body, ScoreInputs.class);
			evaluationsService.updateEvaluation(scores);
			return responseSuccess(scores, HttpStatus.OK, MessageStat.SUCCESS_CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}
	
	@GetMapping("certificate")
	public HttpEntity<?> reportCertificate(@RequestParam String idUser, @RequestParam String idDetailClass) {
		List<?> data = new ArrayList<>();
		byte[] out;
		try {
			data = evaluationsService.getCertificate(idUser, idDetailClass);
			out = JasperUtil.responseToByteArray(data, "Certificate", null);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
//		return new HttpEntity<>(out, headers);
//		return HttpResponse(true, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE, out, headers, data);
		return responseSuccess(data, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE, out, headers);
	}

}
