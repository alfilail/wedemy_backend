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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.elearning.helper.TutorApprovementInputs;
import com.lawencon.elearning.model.ApprovementsRenewal;
import com.lawencon.elearning.service.ApprovementsRenewalService;
import com.lawencon.elearning.util.MessageStat;
import com.lawencon.util.JasperUtil;

@RestController
@RequestMapping("approvement-renewal")
public class ApprovementsRenewalController extends ElearningBaseController {
	@Autowired
	private ApprovementsRenewalService approvementsRenewalService;

	@PostMapping
	public ResponseEntity<?> insertApprovementsRenewal(@RequestBody String body) {
		try {
			ApprovementsRenewal approvementsRenewal = new ObjectMapper().readValue(body, ApprovementsRenewal.class);
			approvementsRenewalService.insertApprovementsRenewal(approvementsRenewal);
			return responseSuccess(approvementsRenewal, HttpStatus.OK, MessageStat.SUCCESS_CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@PostMapping("participant-approvement")
	public ResponseEntity<?> participantApprovementsRenewal(@RequestBody String body) {
		try {
			TutorApprovementInputs approvementsRenewal = new ObjectMapper().readValue(body,
					TutorApprovementInputs.class);
			approvementsRenewalService.participantApprovementsRenewal(approvementsRenewal);
			return responseSuccess(approvementsRenewal, HttpStatus.OK, MessageStat.SUCCESS_CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("participant-presence")
	public ResponseEntity<?> listParticipant(@RequestParam("idDtlClass") String idDtlClass,
			@RequestParam("idDtlModuleRgs") String idDtlModuleRgs) {
		try {
			List<ApprovementsRenewal> listResult = approvementsRenewalService.getListParticipantsPresence(idDtlClass,
					idDtlModuleRgs);
			return responseSuccess(listResult, HttpStatus.OK, MessageStat.SUCCESS_RETRIEVE);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}
	}

	@GetMapping("report/detail")
	public HttpEntity<?> reportAttendanceDetail(@RequestParam("idDtlClass") String idDtlClass,
			@RequestParam("idDtlModuleRgs") String idDtlModuleRgs) {
		byte[] out;
		try {
			List<ApprovementsRenewal> listResult = approvementsRenewalService.getListParticipantsPresence(idDtlClass,
					idDtlModuleRgs);
			out = JasperUtil.responseToByteArray(listResult, "DetailAttendance", null);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		return new HttpEntity<>(out, headers);
	}

	@GetMapping("report/{idDetailClass}")
	public HttpEntity<?> reportPresence(@PathVariable String idDetailClass) {
		List<?> listData = new ArrayList<>();
		byte[] out;
		try {
			listData = approvementsRenewalService.getPresenceReport(idDetailClass);
			out = JasperUtil.responseToByteArray(listData, "Attendance", null);
		} catch (Exception e) {
			e.printStackTrace();
			return responseError(e);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		return new HttpEntity<>(out, headers);
	}

//	@GetMapping("participant")
//	public ResponseEntity<?> getassignmentSubmissionsById(@RequestParam("id") String id) {
//		try {
//			AssignmentSubmissions assignmentSubmissions = assignmentSubmissionsService.getAssignmentSubmissionsById(id);
//			return new ResponseEntity<>(assignmentSubmissions, HttpStatus.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
}
