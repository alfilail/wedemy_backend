package com.lawencon.elearning.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ForumsDao;
import com.lawencon.elearning.model.Forums;

@Service
public class ForumsServiceImpl extends BaseServiceImpl implements ForumsService {

	@Autowired
	private ForumsDao forumsDao;

	@Override
	public void insertForum(Forums forum) throws Exception {
		forum.setForumDateTime(LocalDateTime.now());
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

}
