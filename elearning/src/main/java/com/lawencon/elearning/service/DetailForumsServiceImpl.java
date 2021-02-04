package com.lawencon.elearning.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.DetailForumsDao;
import com.lawencon.elearning.model.DetailForums;

@Service
public class DetailForumsServiceImpl extends ElearningBaseServiceImpl implements DetailForumsService{
	@Autowired
	private DetailForumsDao detailForumDao;
	
	@Override
	public void insertDetailForum(DetailForums detailForum) throws Exception {
		detailForum.setDtlForumDateTime(LocalDateTime.now());
		detailForum.setTrxNumber(generateTrxNumber());
		detailForumDao.insertDetailForum(detailForum, ()->validateInsert(detailForum));
	}
	
	@Override
	public DetailForums getDetailForumById(String id) throws Exception {
		return detailForumDao.getDetailForumById(id);
	}
	
	@Override
	public DetailForums getDetailForumByCode(String code) throws Exception {
		return detailForumDao.getDetailForumByCode(code);
	}
	
	@Override
	public List<DetailForums> getAllDetailForums() throws Exception {
		return detailForumDao.getAllDetailForums();
	}
	
	@Override
	public void updateDetailForum(DetailForums detailForum) throws Exception {
		detailForum.setDtlForumDateTime(LocalDateTime.now());
		detailForumDao.updateDetailForum(detailForum, ()->validateUpdate(detailForum));
	}
	
	@Override
	public void softDeleteDetailForumById(String id, String idUser) throws Exception {
		detailForumDao.softDeleteDetailForumById(id, idUser);
	}
	
	@Override
	public List<DetailForums> getAllDetailForumsByIdForum(String idForum) throws Exception {
		return detailForumDao.getAllDetailForumsByIdForum(idForum);
	}
	
	private void validateInsert(DetailForums detailForum) {

	}
	
	private void validateUpdate(DetailForums detailForum) {

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
