package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.DetailModuleRegistrationsDao;
import com.lawencon.elearning.model.DetailModuleRegistrations;
import com.lawencon.elearning.util.TransactionNumberCode;

@Service
public class DetailModuleRegistrationsServiceImpl extends ElearningBaseServiceImpl
		implements DetailModuleRegistrationsService {

	@Autowired
	private DetailModuleRegistrationsDao dtlModRegistDao;

	@Override
	public void insertDetailModuleRegistration(DetailModuleRegistrations dtlModRegist) throws Exception {
		dtlModRegist.setTrxNumber(generateTrxNumber(TransactionNumberCode.DETAIL_MODULE_REGISTRATION.code));
		dtlModRegistDao.insertDetailModuleRegistration(dtlModRegist, () -> validateInsert(dtlModRegist));
	}

	@Override
	public void update(DetailModuleRegistrations dtlModRegist) throws Exception {
		DetailModuleRegistrations dtlModRgs = dtlModRegistDao.getDetailModuleRegistrationsById(dtlModRegist.getId());
		dtlModRegist.setCreatedAt(dtlModRegist.getCreatedAt());
		dtlModRegist.setCreatedBy(dtlModRgs.getCreatedBy());
		dtlModRegistDao.update(dtlModRegist, () -> validateInsert(dtlModRegist));
	}

	@Override
	public List<DetailModuleRegistrations> getDetailModuleRegistrationsByIdModuleRgs(String idModuleRgs)
			throws Exception {
		return dtlModRegistDao.getDetailModuleRegistrationsByIdModuleRgs(idModuleRgs);
	}

	@Override
	public DetailModuleRegistrations getDetailModuleRegistrationsById(String id) throws Exception {
		return dtlModRegistDao.getDetailModuleRegistrationsById(id);
	}

	@Override
	public Integer totalHours(String idDtlClass) throws Exception {
		return dtlModRegistDao.totalHours(idDtlClass);
	}

	@Override
	public void deleteDetailModuleRegistration(String id, String idUser) throws Exception {
		dtlModRegistDao.deleteDetailModuleRegistration(id, idUser);
	}

	@Override
	public DetailModuleRegistrations getDetailModuleRegistrationByIdLearningMaterial(String id) throws Exception {
		return dtlModRegistDao.getDetailModuleRegistrationByIdLearningMaterial(id);
	}

	private void validateInsert(DetailModuleRegistrations dtlModRegist) throws Exception {
//		if (dtlModRegist.getScheduleDate() != null) {
//			if (dtlModRegist.getScheduleDate()
//					.isAfter(dtlModRegist.getIdModuleRegistration().getIdDetailClass().getEndDate())) {
//				throw new Exception("Jadwal materi tidak bisa melewati masa berlangsung kelas");
//			}
//			if (dtlModRegist.getScheduleDate()
//					.isBefore(dtlModRegist.getIdModuleRegistration().getIdDetailClass().getStartDate())) {
//				throw new Exception("Jadwal materi tidak bisa mendahului masa berlangsung kelas");
//			}
//		} else {
//			throw new Exception("Jadwal materi tidak boleh kosong");
//		}
//		if (dtlModRegist.getOrderNumber() != null) {
//			DetailModuleRegistrations dtlModuleRgs = dtlModRegistDao.getByOrderNumber(dtlModRegist.getOrderNumber());
//			if (dtlModuleRgs != null) {
//				throw new Exception("Order number dalam satu modul tidak boleh sama");
//			}
//		} else {
//			throw new Exception("Order number tidak boleh kosong");
//		}
	}

}
