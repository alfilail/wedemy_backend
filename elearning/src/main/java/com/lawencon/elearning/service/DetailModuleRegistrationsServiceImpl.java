package com.lawencon.elearning.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.DetailModuleRegistrationsDao;
import com.lawencon.elearning.model.DetailModuleRegistrations;

@Service
public class DetailModuleRegistrationsServiceImpl extends ElearningBaseServiceImpl
		implements DetailModuleRegistrationsService {

	@Autowired
	private DetailModuleRegistrationsDao dtlModRegistDao;

	@Override
	public void insertDetailModuleRegistration(DetailModuleRegistrations dtlModRegist) throws Exception {
		dtlModRegist.setTrxNumber(generateTrxNumber());
		dtlModRegistDao.insertDetailModuleRegistration(dtlModRegist, () -> validateInsert(dtlModRegist));
	}

	@Override
	public List<DetailModuleRegistrations> getDetailModuleRegistrationsByIdModuleRgs(String idModuleRgs)
			throws Exception {
		return dtlModRegistDao.getDetailModuleRegistrationsByIdModuleRgs(idModuleRgs);
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

	private String generateTrxNumber() {
		Random random = new Random();
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yy-MM-dd");
		String formattedDate = localDate.format(myFormat);
		String trxCodeValue = String.valueOf(random.nextInt((999 + 1 - 100) + 100));
		String trx = bBuilder(formattedDate).toString();
		trx = trx.replaceAll("-", "");
		String trxNumber = bBuilder("DMRG-", trx, "-", trxCodeValue).toString();
		return trxNumber;
	}

}
