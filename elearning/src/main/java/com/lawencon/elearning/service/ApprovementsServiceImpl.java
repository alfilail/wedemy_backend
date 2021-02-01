package com.lawencon.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.ApprovementsDao;
import com.lawencon.elearning.model.Approvements;

/**
 * @author Nur Alfilail
 */

@Service
public class ApprovementsServiceImpl extends ElearningBaseServiceImpl implements ApprovementsService {

	@Autowired
	private ApprovementsDao approvementsDao;

	@Override
	public void insertApprovement(Approvements approvement) throws Exception {
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
		if (approvement.getApprovementName() == null || approvement.getApprovementName().trim().equals("")) {
			throw new Exception("Nama approvement tidak boleh kosong");
		}
		if (approvement.getCode() == null || approvement.getCode().trim().equals("")) {
			throw new Exception("Kode approvement tidak boleh kosong");
		}
	}

	private void validateUpdate(Approvements approvement) throws Exception {
		if (approvement.getId() == null || approvement.getId().trim().equals("")) {
			throw new Exception("Id approvement tidak boleh kosong");
		}
		if (approvement.getApprovementName() == null || approvement.getApprovementName().trim().equals("")) {
			throw new Exception("Nama approvement tidak boleh kosong");
		}
		if (approvement.getCode() == null || approvement.getCode().trim().equals("")) {
			throw new Exception("Kode approvement tidak boleh kosong");
		}
	}

}
