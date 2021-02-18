package com.lawencon.elearning.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.constant.TransactionNumberCode;
import com.lawencon.elearning.dao.DetailModuleRegistrationsDao;
import com.lawencon.elearning.helper.DetailModuleAndMaterialDoc;
import com.lawencon.elearning.model.ApprovementsRenewal;
import com.lawencon.elearning.model.DetailModuleRegistrations;
import com.lawencon.elearning.model.ModuleRegistrations;

@Service
public class DetailModuleRegistrationsServiceImpl extends ElearningBaseServiceImpl
		implements DetailModuleRegistrationsService {

	@Autowired
	private DetailModuleRegistrationsDao dtlModuleRgsDao;

	@Autowired
	private ApprovementsRenewalService approvementRenewalService;

	@Autowired
	private ModuleRegistrationsService moduleRgsService;

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
		dtlModuleRgsDao.update(dtlModuleRgs, () -> validateUpdate(dtlModuleRgs));
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
		if (dtlModRegist.getIdLearningMaterial().getId() == null) {
			throw new Exception("Id Learning Material tidak boleh kosong");
		}
		if (dtlModRegist.getIdModuleRegistration() != null) {
			ModuleRegistrations moduleRgs = moduleRgsService.getById(dtlModRegist.getIdModuleRegistration().getId());
			if (moduleRgs == null) {
				throw new Exception("Id Module Registration salah");
			}
		} else {
			throw new Exception("Id Module Registration tidak boleh kosong");
		}
		if (dtlModRegist.getScheduleDate() != null) {
			ModuleRegistrations moduleRgs = moduleRgsService.getById(dtlModRegist.getIdModuleRegistration().getId());
			if (dtlModRegist.getScheduleDate().isAfter(moduleRgs.getIdDetailClass().getEndDate())) {
				throw new Exception("Jadwal materi tidak bisa melewati masa berlangsung kelas");
			}
			if (dtlModRegist.getScheduleDate().isBefore(moduleRgs.getIdDetailClass().getStartDate())) {
				throw new Exception("Jadwal materi tidak bisa mendahului masa berlangsung kelas");
			}
		} else {
			throw new Exception("Jadwal materi tidak boleh kosong");
		}
//		if (dtlModRegist.getOrderNumber() != null) {
//			System.out.println(dtlModRegist.getOrderNumber());
//			DetailModuleRegistrations dtlModuleRgs = dtlModuleRgsDao.getDtlModuleRgsByOrderNumber(dtlModRegist);
//			System.out.println(dtlModuleRgs.getOrderNumber());
//			if (dtlModuleRgs != null) {
//				throw new Exception("Order number dalam satu modul tidak boleh sama");
//			}
//		} else {
//			throw new Exception("Order number tidak boleh kosong");
//		}
	}

	private void validateUpdate(DetailModuleRegistrations dtlModuleRgs) throws Exception {
		if (dtlModuleRgs.getId() == null) {
			throw new Exception("Id tidak boleh kosong");
		}
		if (dtlModuleRgs.getVersion() == null) {
			throw new Exception("Version tidak boleh kosong");
		} else {
			DetailModuleRegistrations dm = dtlModuleRgsDao.getDtlModuleRgsById(dtlModuleRgs.getId());
			if (dm.getVersion() != dtlModuleRgs.getVersion()) {
				throw new Exception("Detail Module Registration version tidak sama!");
			}
		}
		if (dtlModuleRgs.getIdLearningMaterial().getId() == null) {
			throw new Exception("Id Learning Material tidak boleh kosong");
		}
		if (dtlModuleRgs.getScheduleDate() != null) {
			if (dtlModuleRgs.getScheduleDate()
					.isAfter(dtlModuleRgs.getIdModuleRegistration().getIdDetailClass().getEndDate())) {
				throw new Exception("Jadwal materi tidak bisa melewati masa berlangsung kelas");
			}
			if (dtlModuleRgs.getScheduleDate()
					.isBefore(dtlModuleRgs.getIdModuleRegistration().getIdDetailClass().getStartDate())) {
				throw new Exception("Jadwal materi tidak bisa mendahului masa berlangsung kelas");
			}
		} else {
			throw new Exception("Jadwal materi tidak boleh kosong");
		}
//		if (dtlModuleRgs.getOrderNumber() != null) {
//			DetailModuleRegistrations formerData1 = dtlModuleRgsDao.getDtlModuleRgsById(dtlModuleRgs.getId());
//			if (!dtlModuleRgs.getOrderNumber().equals(formerData1.getOrderNumber())) {
//				DetailModuleRegistrations formerData2 = dtlModuleRgsDao.getDtlModuleRgsByOrderNumber(dtlModuleRgs);
//				if (formerData2 != null) {
//					throw new Exception("Order number dalam satu modul tidak boleh sama");
//				}
//			}
//		} else {
//			throw new Exception("Order number tidak boleh kosong");
//		}
	}

	@Override
	public List<DetailModuleAndMaterialDoc> getAllModuleAndLearningMaterialByIdDetailClass(String idDetailClass)
			throws Exception {
		List<DetailModuleRegistrations> list = dtlModuleRgsDao
				.getAllModuleAndLearningMaterialsByIdDetailClass(idDetailClass);
		List<DetailModuleAndMaterialDoc> listResult = new ArrayList<DetailModuleAndMaterialDoc>();
		for (DetailModuleRegistrations detail : list) {
			List<ApprovementsRenewal> listRes = approvementRenewalService.getAllParticipantPresences(
					detail.getIdModuleRegistration().getIdDetailClass().getId(), detail.getId());
			DetailModuleAndMaterialDoc dt = new DetailModuleAndMaterialDoc();
			dt.setDetailModule(detail);
			boolean checkRes = validateCheckDownload(detail, listRes);
			if (checkRes) {
				dt.setCheckDownload(true);
			} else {
				dt.setCheckDownload(false);
			}
			listResult.add(dt);
		}
		return listResult;
	}

	private boolean validateCheckDownload(DetailModuleRegistrations detail, List<ApprovementsRenewal> listRes)
			throws Exception {
		boolean checkEnrollClass = false, checkScheduleDate = false;
		if (listRes.size() > 0) {
			checkEnrollClass = true;
		} else {
			checkEnrollClass = false;
		}
		if (LocalDate.now().compareTo(detail.getScheduleDate()) >= 0) {
			checkScheduleDate = true;
		} else {
			checkScheduleDate = false;
		}
		if (checkEnrollClass == true && checkScheduleDate == true) {
			return true;
		} else {
			return false;
		}
	}
}
