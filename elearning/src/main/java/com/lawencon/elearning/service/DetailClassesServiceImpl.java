package com.lawencon.elearning.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.DetailClassesDao;
import com.lawencon.elearning.helper.ClassesHelper;
import com.lawencon.elearning.helper.DetailClassInformation;
import com.lawencon.elearning.model.Classes;
import com.lawencon.elearning.model.DetailClasses;
import com.lawencon.elearning.model.DetailModuleRegistrations;
import com.lawencon.elearning.model.ModuleRegistrations;
import com.lawencon.elearning.model.Modules;

@Service
public class DetailClassesServiceImpl extends ElearningBaseServiceImpl implements DetailClassesService {
	@Autowired
	private DetailClassesDao detailClassesDao;

	@Autowired
	private ClassesService classService;

	@Autowired
	private ClassEnrollmentService classEnrollmentService;

	@Autowired
	private ModulesService modulesService;

	@Autowired
	private ModuleRegistrationsService moduleRegistrationsService;

	@Autowired
	private DetailModuleRegistrationsService detailModuleRegistrationsService;

	@Override
	public void insert(DetailClasses detailClass) throws Exception {
		Classes clazz = classService.getById(detailClass.getIdClass().getId());
		detailClass.setCode(generateCodeDetailClass(clazz.getCode(), detailClass.getStartDate()));
		detailClassesDao.insert(detailClass, () -> validateInsert(detailClass));
	}

	@Override
	public List<DetailClasses> getAll() throws Exception {
		return detailClassesDao.getAllDetailClass();
	}

	@Override
	public List<DetailClasses> getAllByIdClass(String idClass) throws Exception {
		return detailClassesDao.getAllByIdClass(idClass);
	}

	@Override
	public DetailClasses getById(String id) throws Exception {
		return detailClassesDao.getDetailClassById(id);
	}

	@Override
	public DetailClassInformation getInformationByIdDetailClass(String idDtlClass) throws Exception {
		DetailClassInformation dtlClassInfo = new DetailClassInformation();
		try {
			begin();
			updateViews(idDtlClass);
			dtlClassInfo.setDetailClass(detailClassesDao.getDetailClassById(idDtlClass));
			dtlClassInfo.setModules(moduleRegistrationsService.getByIdDtlClass(idDtlClass));
			dtlClassInfo.setTotalParticipant(classEnrollmentService.getTotalParticipantsByIdDtlClass(idDtlClass));
			dtlClassInfo.setTotalHours(detailModuleRegistrationsService.totalHours(idDtlClass));
			commit();
		} catch (Exception e) {
			rollback();
			e.printStackTrace();
		}
		return dtlClassInfo;
	}

	@Override
	public DetailClasses getByCode(String code) throws Exception {
		return detailClassesDao.getByCode(code);
	}

	@Override
	public List<DetailClasses> getTutorClasses(String idTutor) throws Exception {
		return detailClassesDao.getTutorClasses(idTutor);
	}

	@Override
	public List<DetailClasses> getPopularClasses() throws Exception {
		return detailClassesDao.getPopularClasses();
	}

	@Override
	public void deleteById(String id, String idUser) throws Exception {
		detailClassesDao.deleteById(id, idUser);
	}

	@Override
	public void reactiveOldClass(DetailClasses detailClass) throws Exception {
		try {
			begin();
			Classes clazz = classService.getInActiveById(detailClass.getIdClass().getId());

			classService.updateIsActive(detailClass.getIdClass().getId(), detailClass.getCreatedBy());

			detailClass.setCode(generateCodeDetailClass(clazz.getCode(), detailClass.getStartDate()));
			detailClass.setViews(0);
			detailClass.setIdClass(clazz);

			DetailClasses detailClassOld = detailClassesDao.getByIdClass(detailClass.getIdClass().getId());

			List<ModuleRegistrations> modulesRegistrationListOld = moduleRegistrationsService
					.getModuleRegistrationsByIdDetailClass(detailClassOld.getId());

			List<Modules> modulesList = new ArrayList<Modules>();

			List<DetailModuleRegistrations> detailModuleList = new ArrayList<DetailModuleRegistrations>();

			for (ModuleRegistrations moduleRegistration : modulesRegistrationListOld) {
				Modules module = modulesService.getById(moduleRegistration.getIdModule().getId());
				modulesList.add(module);

				List<DetailModuleRegistrations> detailModuleRegis = detailModuleRegistrationsService
						.getDetailModuleRegistrationsByIdModuleRgs(moduleRegistration.getId());
				
				for (DetailModuleRegistrations detailModule : detailModuleRegis) {
					DetailModuleRegistrations detail = new DetailModuleRegistrations();
					detail.setIdLearningMaterial(detailModule.getIdLearningMaterial());
					detail.setIdModuleRegistration(detailModule.getIdModuleRegistration());
					detail.setOrderNumber(detailModule.getOrderNumber());
					detail.setScheduleDate(detailModule.getScheduleDate());
					detailModuleList.add(detail);
				}
			}
			detailClassesDao.insert(detailClass, () -> validateReactive(detailClass));

			ClassesHelper clazzHelper = new ClassesHelper();
			clazzHelper.setClazz(clazz);
			clazzHelper.setDetailClass(detailClass);
			clazzHelper.setModule(modulesList);
			moduleRegistrationsService.insertModuleRegistration(clazzHelper);

			for (DetailModuleRegistrations detailModuleRegis : detailModuleList) {
				detailModuleRegistrationsService.insertDetailModuleRegistration(detailModuleRegis);
			}
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
	}

	private void validateInsert(DetailClasses detailClass) throws Exception {
		if (detailClass.getStartDate() == null) {
			throw new Exception("Tanggal mulai detail kelas tidak boleh kosong!");
		} else if (detailClass.getEndDate() == null) {
			throw new Exception("Tanggal akhir detail kelas tidak boleh kosong!");
		} else if (detailClass.getStartTime() == null) {
			throw new Exception("Waktu mulai detail kelas tidak boleh kosong!");
		} else if (detailClass.getEndTime() == null) {
			throw new Exception("Waktu akhir detail kelas tidak boleh kosong!");
		}
	}

	private void validateReactive(DetailClasses detailClass) throws Exception {
		validateInsert(detailClass);
		DetailClasses detailClazz = detailClassesDao.getByIdClass(detailClass.getIdClass().getId());
		if (detailClass.getStartDate().compareTo(detailClazz.getEndDate()) < 0) {
			throw new Exception(
					"Tanggal mulai detail kelas tidak boleh kurang dari tanggal akhir detail kelas sebelumnya");
		}

	}

	private String generateCodeDetailClass(String classCode, LocalDate startDate) {
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yy-MM-dd");
		String formattedDate = localDate.format(myFormat);
		String date = bBuilder(formattedDate).toString();
		date = date.replaceAll("-", "");
		String detailClassCode = bBuilder(classCode, date).toString();
		return detailClassCode;
	}

	private void updateViews(String id) throws Exception {
		detailClassesDao.updateViews(id);
	}

	@Override
	public void update(DetailClasses dtlClass) throws Exception {
		detailClassesDao.update(dtlClass, () -> validateUpdate(dtlClass));
	}

	private void validateUpdate(DetailClasses dtlClass) throws Exception {
		if (dtlClass.getStartDate() == null) {
			throw new Exception("Tanggal mulai detail kelas tidak boleh kosong!");
		}
		if (dtlClass.getEndDate() == null) {
			throw new Exception("Tanggal akhir detail kelas tidak boleh kosong!");
		} else {
			if (dtlClass.getEndDate().compareTo(dtlClass.getStartDate()) < 0) {
				throw new Exception("Tanggal akhir detail kelas tidak boleh kurang dari tanggal mulai");
			}
		}
		if (dtlClass.getStartTime() == null) {
			throw new Exception("Waktu mulai detail kelas tidak boleh kosong!");
		}
		if (dtlClass.getEndTime() == null) {
			throw new Exception("Waktu akhir detail kelas tidak boleh kosong!");
		} else {
			if (dtlClass.getEndTime().compareTo(dtlClass.getStartTime()) < 0) {
				throw new Exception("Waktu akhir detail kelas tidak boleh kurang dari waktu akhir");
			}
		}
	}

	@Override
	public List<DetailClasses> getAllInactive() throws Exception {
		return detailClassesDao.getAllInactive();
	}

}