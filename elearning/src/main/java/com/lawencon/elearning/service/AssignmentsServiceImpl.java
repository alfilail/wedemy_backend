package com.lawencon.elearning.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.AssignmentsDao;
import com.lawencon.elearning.model.Assignments;

@Service
public class AssignmentsServiceImpl extends BaseServiceImpl implements AssignmentsService {

	@Autowired
	private AssignmentsDao assignmentsDao;

	@Override
	public void insertAssignment(Assignments assignments, MultipartFile file) throws Exception {
		assignments.setCreatedAt(LocalDateTime.now());
		assignments.setUpdatedAt(LocalDateTime.now());
		assignments.setFile(file.getBytes());
		assignments.setFileType(file.getContentType());
//		try {
			begin();
			assignmentsDao.insertAssignment(assignments, () -> validateInsert(assignments));
			commit();			
//		} catch(Exception e) {
//			rollback();
//			throw new Exception(e);
//		}
	}

	@Override
	public List<Assignments> getAllAssignments() throws Exception {
		return assignmentsDao.getAllAssignments();
	}

	@Override
	public Assignments getAssignmentsById(String id) throws Exception {
		return assignmentsDao.getAssignmentsById(id);
	}

	@Override
	public Assignments getAssignmentsByCode(String code) throws Exception {
		return assignmentsDao.getAssignmentsByCode(code);
	}

	private void validateInsert(Assignments assignments) {
		// validate here
	}

}
