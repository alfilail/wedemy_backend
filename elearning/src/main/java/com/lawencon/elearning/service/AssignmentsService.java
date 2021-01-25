package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lawencon.elearning.model.Assignments;

public interface AssignmentsService {
	
	void insertAssignment(Assignments assignments, MultipartFile file) throws Exception;

	List<Assignments> getAllAssignments() throws Exception;

	Assignments getAssignmentsById(String id) throws Exception;
	
	Assignments getAssignmentsByCode(String code) throws Exception;

}
