package com.lawencon.elearning.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.ForumsDao;
import com.lawencon.elearning.helper.ForumAndDetailForums;
import com.lawencon.elearning.model.DetailForums;
import com.lawencon.elearning.model.Forums;

@Service
public class ForumsServiceImpl extends ElearningBaseServiceImpl implements ForumsService {

	@Autowired
	private ForumsDao forumsDao;

	@Autowired
	private DetailForumsService detailForumService;

	@Override
	public void insertForum(Forums forum) throws Exception {
		forum.setForumDateTime(LocalDateTime.now());
		forum.setTrxNumber(generateTrxNumber());
		forumsDao.insertForum(forum, () -> validateInsert(forum));
	}

	@Override
	public void updateContentForum(Forums forum) throws Exception {
		forumsDao.updateContentForum(forum, () -> validateUpdate(forum));
	}

	@Override
	public void deleteForumByIdDetailModuleRegistration(String idDetailModuleRegistration, String idUser) throws Exception {
		begin();
		List<Forums> forumList = forumsDao.getForumByIdDetailModuleRegistration(idDetailModuleRegistration);
		for(Forums forum : forumList) {
			forumsDao.softDeleteForumById(forum.getId(), idUser);
			List<DetailForums> detailForumList = detailForumService.getAllDetailForumsByIdForum(forum.getId());
			for(DetailForums detailForum : detailForumList) {
				detailForumService.deleteDetailForumById(detailForum.getId());
			}
		}
//		if (validateDelete(forum.getId()) == true) {
//			forumsDao.softDeleteForumById(forum.getId(), idUser);
//		} else {
//			forumsDao.deleteForumById(forum.getId());
//		}
		commit();
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
	public List<ForumAndDetailForums> getForumByIdDetailModuleRegistration(String id) throws Exception {
		List<ForumAndDetailForums> listResult = new ArrayList<>();
		List<Forums> forums = forumsDao.getForumByIdDetailModuleRegistration(id);
		for (Forums forum : forums) {
			List<DetailForums> detailForums = detailForumService.getAllDetailForumsByIdForum(forum.getId());
			ForumAndDetailForums result = new ForumAndDetailForums();
			result.setForum(forum);
			result.setDetailForums(detailForums);
			listResult.add(result);
		}
		return listResult;
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
		String trxNumber = bBuilder("ASB-", trx, "-", trxCodeValue).toString();
		return trxNumber;
	}

//	private boolean validateDelete(String id) throws Exception {
//		List<?> listObj = forumsDao.validateDeleteForum(id);
//		listObj.forEach(System.out::println);
//		List<?> list = listObj.stream().filter(val -> val != null).collect(Collectors.toList());
//		System.out.println(list.size());
//		return list.size() > 0 ? true : false;
//	}

}
