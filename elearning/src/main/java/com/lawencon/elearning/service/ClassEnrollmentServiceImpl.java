package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.ClassEnrollmentsDao;
import com.lawencon.elearning.model.ClassEnrollments;
import com.lawencon.elearning.model.DetailClasses;
import com.lawencon.elearning.util.TransactionNumberCode;

@Service
public class ClassEnrollmentServiceImpl extends ElearningBaseServiceImpl implements ClassEnrollmentService {
	@Autowired
	private ClassEnrollmentsDao classEnrollmentDao;

	@Autowired
	private DetailClassesService detailClassService;

	@Override
	public void insertClassEnrollment(ClassEnrollments classEnrollment) throws Exception {
		try {
			begin();
			classEnrollment.setTrxNumber(generateTrxNumber(TransactionNumberCode.CLASS_ENROLLMENT.code));
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

	private void validateInsert(ClassEnrollments classEnrollment) throws Exception {
		Integer totalParticipant = getTotalParticipantsByIdDtlClass(classEnrollment.getIdDetailClass().getId());
		DetailClasses detailClass = detailClassService.getById(classEnrollment.getIdDetailClass().getId());
		if (totalParticipant >= detailClass.getIdClass().getQuota()) {
			throw new Exception("Quota kelas sudah penuh!");
		}
	}

	private void validateUpdate(ClassEnrollments classEnrollment) throws Exception {

	}
}
