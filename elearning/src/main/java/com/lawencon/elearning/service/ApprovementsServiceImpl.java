package com.lawencon.elearning.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ApprovementsDao;
import com.lawencon.elearning.model.Approvements;

/**
 * @author Nur Alfilail
 */

@Service
public class ApprovementsServiceImpl extends BaseServiceImpl implements ApprovementsService {

	@Autowired
	private ApprovementsDao approvementsDao;

	@Override
	public void insertApprovement(Approvements approvement) throws Exception {
		approvement.setCreatedAt(LocalDateTime.now());
		approvement.setUpdatedAt(LocalDateTime.now());
		approvementsDao.insertApprovement(approvement, () -> validateInsert(approvement));
	}

	@Override
	public List<Approvements> getAllApprovements() throws Exception {
		return approvementsDao.getAllApprovements();
	}

	@Override
	public Approvements getApprovementsById(String id) throws Exception {
		return approvementsDao.getApprovementsById(id);
	}

	@Override
	public void updateApprovement(Approvements approvement) throws Exception {
		approvement.setUpdatedAt(LocalDateTime.now());
		approvementsDao.updateApprovement(approvement, () -> validateUpdate(approvement));
	}

	@Override
	public void deleteApprovementById(String id) throws Exception {
		approvementsDao.deleteApprovementById(id);
	}

	@Override
	public Approvements getApprovementByCode(String code) throws Exception {
		return approvementsDao.getApprovementByCode(code);
	}

	private void validateInsert(Approvements approvement) throws Exception {
		Approvements approve = getApprovementByCode(approvement.getCode());
		if(approve != null) {
			if(approve.getCode().equals(approvement.getCode())) {
				
			}
		}
	}

	private void validateUpdate(Approvements approvement) throws Exception {

	}

}
