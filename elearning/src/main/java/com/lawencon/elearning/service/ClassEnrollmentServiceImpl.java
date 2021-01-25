package com.lawencon.elearning.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.ClassEnrollmentsDao;
import com.lawencon.elearning.model.ClassEnrollments;

@Service
public class ClassEnrollmentServiceImpl extends ElearningBaseServiceImpl implements ClassEnrollmentService {
	@Autowired
	private ClassEnrollmentsDao classEnrollmentDao;
	
	@Override
	public void insertClassEnrollment(ClassEnrollments classEnrollment) throws Exception {
		classEnrollment.setCreatedAt(LocalDateTime.now());
		classEnrollment.setTrxNumber(generateTrxNumber());
		classEnrollmentDao.insertClassEndrollment(classEnrollment, ()-> validateInsert(classEnrollment));
	}
	
	@Override
	public List<ClassEnrollments> getAllClassEnrollments() throws Exception {
		return classEnrollmentDao.getAllClassEnrollments();
	}
	
	@Override
	public ClassEnrollments getClassEnrollmentByCode(String code) throws Exception {
		return classEnrollmentDao.getclassEnrollmentByCode(code);
	}
	
	@Override
	public ClassEnrollments getClassEnrollmentsById(String id) throws Exception {
		return classEnrollmentDao.getClassEnrollmentById(id);
	}
	
	@Override
	public void updateClassEnrollments(ClassEnrollments classEnrollment) throws Exception {
		classEnrollmentDao.updateClassEnrollment(classEnrollment, ()-> validateUpdate(classEnrollment));
	}
	
	@Override
	public void deleteClassEnrollmentsById(String id) throws Exception {
		classEnrollmentDao.deleteclassEnrollmentById(id);
	}
	
	private void validateInsert(ClassEnrollments classEnrollment) throws Exception {

	}

	private void validateUpdate(ClassEnrollments classEnrollment) throws Exception {

	}
	
	private String generateTrxNumber() {
		Random random = new Random();
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yy-MM-dd");
		String formattedDate = localDate.format(myFormat);
		String trxCodeValue = String.valueOf(random.nextInt((999 + 1 - 100) + 100));
		String trx = bBuilder(formattedDate).toString();
		trx = trx.replaceAll("-", "");
		String trxNumber= bBuilder("CER-", trx, "-",trxCodeValue).toString();
		return trxNumber;
	}
}
