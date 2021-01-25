package com.lawencon.elearning.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.elearning.dao.AssignmentsDao;
import com.lawencon.elearning.model.Assignments;

@Service
public class AssignmentsServiceImpl extends ElearningBaseServiceImpl implements AssignmentsService {

	@Autowired
	private AssignmentsDao assignmentsDao;

	@Override
	public void insertAssignment(Assignments assignments, MultipartFile file) throws Exception {
		assignments.setCreatedAt(LocalDateTime.now());
		assignments.setFile(file.getBytes());
		assignments.setFileType(file.getContentType());
		assignments.setTrxNumber(generateTrxNumber());
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
	
	private String generateTrxNumber() {
		Random random = new Random();
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yy-MM-dd");
		String formattedDate = localDate.format(myFormat);
		String trxCodeValue = String.valueOf(random.nextInt((999 + 1 - 100) + 100));
		String trx = bBuilder(formattedDate).toString();
		trx = trx.replaceAll("-", "");
		String trxNumber= bBuilder("ASB-", trx, "-",trxCodeValue).toString();
		return trxNumber;
	}

}
