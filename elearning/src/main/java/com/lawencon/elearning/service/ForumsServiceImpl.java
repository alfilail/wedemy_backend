package com.lawencon.elearning.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.ForumsDao;
import com.lawencon.elearning.model.Forums;

@Service
public class ForumsServiceImpl extends ElearningBaseServiceImpl implements ForumsService {

	@Autowired
	private ForumsDao forumsDao;

	@Override
	public void insertForum(Forums forum) throws Exception {
		forum.setForumDateTime(LocalDateTime.now());
		forum.setTrxNumber(generateTrxNumber());
		try {
			begin();
			forumsDao.insertForum(forum, () -> validateInsert(forum));
			commit();
		} catch (Exception e) {
			rollback();
			throw new Exception(e);
		}
	}

	@Override
	public void updateContentForum(Forums forum) throws Exception {
		try {
			begin();
			forumsDao.updateContentForum(forum, () -> validateUpdate(forum));
			commit();
		} catch (Exception e) {
			rollback();
			throw new Exception(e);
		}
	}

	@Override
	public void deleteForumById(String id) throws Exception {
		forumsDao.deleteForumById(id);
	}

	@Override
	public List<Forums> getAllForums() throws Exception {
		return forumsDao.getAllForums();
	}

	@Override
	public Forums getForumById(String id) throws Exception {
		return forumsDao.getForumById(id);
	}

	@Override
	public Forums getForumByIdDetailModuleRegistration(String id) throws Exception {
		return forumsDao.getForumByIdDetailModuleRegistration(id);
	}

	private void validateInsert(Forums forum) throws Exception {

	}

	private void validateUpdate(Forums forum) throws Exception {

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
