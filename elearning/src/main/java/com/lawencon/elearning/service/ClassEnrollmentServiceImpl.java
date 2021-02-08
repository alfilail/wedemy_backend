package com.lawencon.elearning.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.ClassEnrollmentsDao;
import com.lawencon.elearning.model.ClassEnrollments;
import com.lawencon.elearning.model.DetailClasses;

@Service
public class ClassEnrollmentServiceImpl extends ElearningBaseServiceImpl implements ClassEnrollmentService {
	@Autowired
	private ClassEnrollmentsDao classEnrollmentDao;

	@Autowired
	private DetailClassesService detailClassService;

	@Override
	public void insertClassEnrollment(ClassEnrollments classEnrollment) throws Exception {
		try {
			classEnrollment.setTrxNumber(generateTrxNumber());
			classEnrollment.setIsOngoing(true);
			classEnrollmentDao.insertClassEnrollment(classEnrollment, () -> validateInsert(classEnrollment));
			commit();
		} catch (Exception e) {
			rollback();
			throw new Exception(e);
		}
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
	public List<DetailClasses> getAllClassEnrollmentsByIdUser(String id) throws Exception {
		return classEnrollmentDao.getAllClassEnrollmentsByIdUser(id);
	}

	@Override
	public ClassEnrollments getClassEnrollmentByIdDtlClassAndIdUser(String idDtlClass, String idUser) {
		return classEnrollmentDao.getClassEnrollmentByIdDtlClassAndIdUser(idDtlClass, idUser);
	}

	@Override
	public Integer getTotalParticipantsByIdDtlClass(String id) throws Exception {
		return classEnrollmentDao.getTotalParticipantsByIdDtlClass(id);
	}

	@Override
	public void updateClassEnrollments(ClassEnrollments classEnrollment) throws Exception {
		classEnrollmentDao.updateClassEnrollment(classEnrollment, () -> validateUpdate(classEnrollment));
	}

	@Override
	public void deleteClassEnrollmentsById(String id) throws Exception {
		classEnrollmentDao.deleteclassEnrollmentById(id);
	}

	@Override
	public List<?> getCertificate(String idUser, String idClass) throws Exception {
		return classEnrollmentDao.getCertificate(idUser, idClass);
	}

	private void validateInsert(ClassEnrollments classEnrollment) throws Exception {
		Integer totalParticipant = getTotalParticipantsByIdDtlClass(classEnrollment.getIdDetailClass().getId());
//		System.out.println(totalParticipant);
		DetailClasses detailClass = detailClassService.getDetailClassById(classEnrollment.getIdDetailClass().getId());
//		System.out.println(detailClass.getIdClass().getQuota());
		if (totalParticipant >= detailClass.getIdClass().getQuota()) {
			throw new Exception("Quota kelas sudah penuh!");
		}
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
		String trxNumber = bBuilder("CER-", trx, "-", trxCodeValue).toString();
		return trxNumber;
	}
}
