package com.lawencon.elearning.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.DetailModuleRegistrationsDao;
import com.lawencon.elearning.model.DetailModuleRegistrations;

@Service
public class DetailModuleRegistrationsServiceImpl extends ElearningBaseServiceImpl implements DetailModuleRegistrationsService {
	
	@Autowired
	private DetailModuleRegistrationsDao dtlModRegistDao;

	@Override
	public void insertDetailModuleRegistration(DetailModuleRegistrations dtlModRegist) throws Exception {
		dtlModRegist.setTrxNumber(generateTrxNumber());
		dtlModRegistDao.insertDetailModuleRegistration(dtlModRegist, () -> validateInput(dtlModRegist));
	}
	
	private void validateInput(DetailModuleRegistrations dtlModRegist) {
		
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
