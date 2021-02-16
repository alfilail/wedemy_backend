package com.lawencon.elearning.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.DetailModuleRegistrationsDao;
import com.lawencon.elearning.helper.DetailModuleAndMaterialDoc;
import com.lawencon.elearning.model.ApprovementsRenewal;
import com.lawencon.elearning.model.DetailModuleRegistrations;
import com.lawencon.elearning.constant.TransactionNumberCode;

@Service
public class DetailModuleRegistrationsServiceImpl extends ElearningBaseServiceImpl
		implements DetailModuleRegistrationsService {

	@Autowired
	private DetailModuleRegistrationsDao dtlModuleRgsDao;
	
	@Autowired
	private ApprovementsRenewalService approvementRenewalService;

	@Override
	public void insert(DetailModuleRegistrations dtlModuleRgs) throws Exception {
		dtlModuleRgs.setTrxNumber(generateTrxNumber(TransactionNumberCode.DETAIL_MODULE_REGISTRATION.code));
		dtlModuleRgsDao.insert(dtlModuleRgs, () -> validateInsert(dtlModuleRgs));
	}

	@Override
	public void update(DetailModuleRegistrations dtlModuleRgs) throws Exception {
		DetailModuleRegistrations dtlModRgs = dtlModuleRgsDao.getDtlModuleRgsById(dtlModuleRgs.getId());
		dtlModuleRgs.setCreatedAt(dtlModuleRgs.getCreatedAt());
		dtlModuleRgs.setCreatedBy(dtlModRgs.getCreatedBy());
		dtlModuleRgs.setIdModuleRegistration(dtlModRgs.getIdModuleRegistration());
		dtlModuleRgsDao.update(dtlModuleRgs, () -> validateInsert(dtlModuleRgs));
	}

	@Override
	public void deleteById(String id, String idUser) throws Exception {
		dtlModuleRgsDao.deleteById(id, idUser);
	}

	@Override
	public DetailModuleRegistrations getDtlModuleRgsById(String id) throws Exception {
		return dtlModuleRgsDao.getDtlModuleRgsById(id);
	}

	@Override
	public DetailModuleRegistrations getDtlModuleRgsByIdLearningMaterial(String id) throws Exception {
		return dtlModuleRgsDao.getDtlModuleRgsByIdLearningMaterial(id);
	}

	@Override
	public Integer totalHours(String idDtlClass) throws Exception {
		return dtlModuleRgsDao.totalHours(idDtlClass);
	}

	@Override
	public List<DetailModuleRegistrations> getAllByIdModuleRgs(String idModuleRgs) throws Exception {
		return dtlModuleRgsDao.getAllByIdModuleRgs(idModuleRgs);
	}

	private void validateInsert(DetailModuleRegistrations dtlModRegist) throws Exception {
		if (dtlModRegist.getScheduleDate() != null) {
			if (dtlModRegist.getScheduleDate()
					.isAfter(dtlModRegist.getIdModuleRegistration().getIdDetailClass().getEndDate())) {
				throw new Exception("Jadwal materi tidak bisa melewati masa berlangsung kelas");
			}
			if (dtlModRegist.getScheduleDate()
					.isBefore(dtlModRegist.getIdModuleRegistration().getIdDetailClass().getStartDate())) {
				throw new Exception("Jadwal materi tidak bisa mendahului masa berlangsung kelas");
			}
		} else {
			throw new Exception("Jadwal materi tidak boleh kosong");
		}
		if (dtlModRegist.getOrderNumber() != null) {
			DetailModuleRegistrations dtlModuleRgs = dtlModuleRgsDao
					.getDtlModuleRgsByOrderNumber(dtlModRegist.getOrderNumber());
			if (dtlModuleRgs != null) {
				throw new Exception("Order number dalam satu modul tidak boleh sama");
			}
		} else {
			throw new Exception("Order number tidak boleh kosong");
		}
	}
	
	@Override
	public List<DetailModuleAndMaterialDoc> getAllModuleAndLearningMaterialByIdDetailClass(String idDetailClass) throws Exception {
		List<DetailModuleRegistrations>  list = dtlModuleRgsDao.getAllModuleAndLearningMaterialsByIdDetailClass(idDetailClass);
		List<DetailModuleAndMaterialDoc> listResult = new ArrayList<DetailModuleAndMaterialDoc>();
		for(DetailModuleRegistrations detail : list) {
			List<ApprovementsRenewal> listRes = approvementRenewalService.getAllParticipantPresences(
					detail.getIdModuleRegistration().getIdDetailClass().getId(), detail.getId());
			DetailModuleAndMaterialDoc dt = new DetailModuleAndMaterialDoc();
			dt.setDetailModule(detail);
			if(listRes.size() > 0) {
				dt.setCheckDownload(true);
			}
			else {
				dt.setCheckDownload(false);
			}
			listResult.add(dt);
		}
		return listResult;
	}

}
