package com.lawencon.elearning.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.DetailClassesDao;
import com.lawencon.elearning.helper.ClassesHelper;
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
	public void insertDetailClass(DetailClasses detailClass) throws Exception {
		Classes clazz = classService.getClassById(detailClass.getIdClass().getId());
		detailClass.setCode(generateCodeDetailClass(clazz.getCode(), detailClass.getStartDate()));
		detailClassesDao.insertDetailClass(detailClass, () -> validateInsert(detailClass));
	}
	
	@Override
	public List<DetailClasses> getAllDetailClass() throws Exception {
		return detailClassesDao.getAllDetailClass();
	}
	
	@Override
	public List<DetailClasses> getAllDetailClassByIdClass(String idClass) throws Exception {
		return detailClassesDao.getAllDetailClassByIdClass(idClass);
	}

	@Override
	public DetailClasses getDetailClassById(String id) throws Exception {
		DetailClasses dtlClass = new DetailClasses();
		try {
			begin();
			dtlClass = detailClassesDao.getDetailClassById(id);
			updateViews(id);
			dtlClass.setTotalParticipant(classEnrollmentService.getTotalParticipantsByIdDtlClass(id));
			commit();
		} catch (Exception e) {
			rollback();
			e.printStackTrace();
		}
		return dtlClass;
	}

	@Override
	public DetailClasses getDetailClassByCode(String code) throws Exception {
		return detailClassesDao.getDetailClassByCode(code);
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
	public void deleteDetailClassById(String id, String idUser) throws Exception {
		detailClassesDao.deleteClassById(id, idUser);
	}
	
	@Override
	public void reactiveOldClass(DetailClasses detailClass) throws Exception {
		try {
		begin();
		//get class by id class
		Classes clazz = classService.getInActiveClassById(detailClass.getIdClass().getId());
		
		//update class is active
		classService.updateClassIsActive(detailClass.getIdClass().getId(), detailClass.getCreatedBy());
		
		//set detail class
		detailClass.setCode(generateCodeDetailClass(clazz.getCode(), detailClass.getStartDate()));
		detailClass.setViews(0);
		detailClass.setIdClass(clazz);
		
		//get detail class lama yang terbaru (end date tertinggi)
		DetailClasses detailClassOld = detailClassesDao.getDetailClassByIdClass(detailClass.getIdClass().getId());
		
		//get all module registration by id detail class lama
		List<ModuleRegistrations> modulesRegistrationListOld = 
				moduleRegistrationsService.getModuleRegistrationsByIdDetailClass(detailClassOld.getId());
		
		// untuk menampung module list nya
		List<Modules> modulesList = new ArrayList<Modules>();
		
		//untuk menampung module registrations list
		List<DetailModuleRegistrations> detailModuleList = new ArrayList<DetailModuleRegistrations>();
		
		for(ModuleRegistrations moduleRegistration : modulesRegistrationListOld) {
			//get module by id module yang ada di module registration
			Modules module = modulesService.getModuleById(moduleRegistration.getIdModule().getId());
			modulesList.add(module);
			
			List<DetailModuleRegistrations> detailModuleRegis = 
					detailModuleRegistrationsService
					.getDetailModuleRegistrationsByIdModuleRgs(moduleRegistration.getId());

			// memindahkan detail module registration by id module registration ke detail module registration list
			for(DetailModuleRegistrations detailModule : detailModuleRegis) {
				DetailModuleRegistrations detail = new DetailModuleRegistrations();
				detail.setIdLearningMaterial(detailModule.getIdLearningMaterial());
				detail.setIdModuleRegistration(detailModule.getIdModuleRegistration());
				detail.setOrderNumber(detailModule.getOrderNumber());
				detail.setScheduleDate(detailModule.getScheduleDate());
				detailModuleList.add(detail);	
			}
		}	
		
		detailClassesDao.insertDetailClass(detailClass, ()->validateReactive(detailClass));
		
		//membuat clazz helper untuk insert module registration
		ClassesHelper clazzHelper = new ClassesHelper();
		clazzHelper.setClazz(clazz);
		clazzHelper.setDetailClass(detailClass);
		clazzHelper.setModule(modulesList);
		moduleRegistrationsService.insertModuleRegistration(clazzHelper);
		
		//insert detail module registration
		for(DetailModuleRegistrations detailModuleRegis : detailModuleList) {
			detailModuleRegistrationsService.insertDetailModuleRegistration(detailModuleRegis);
		}
		commit();
		}
		catch (Exception e) {
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
		DetailClasses detailClazz = detailClassesDao.getDetailClassByIdClass(detailClass.getIdClass().getId());
		if(detailClass.getStartDate().compareTo(detailClazz.getEndDate())< 0) {
			throw new Exception("Tanggal mulai detail kelas tidak boleh kurang dari tanggal akhir detail kelas sebelumnya");
		}
		
	}

	private String generateCodeDetailClass(String classCode, LocalDate startDate) {
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yy-MM-dd");
		String formattedDate = startDate.format(myFormat);
		String date = bBuilder(formattedDate).toString();
		date = date.replaceAll("-", "");
		String detailClassCode = bBuilder(classCode, date).toString();
		return detailClassCode;
	}

	private void updateViews(String id) throws Exception {
		detailClassesDao.updateViews(id);
	}

	@Override
	public void updateDetailClass(DetailClasses dtlClass) throws Exception {
		detailClassesDao.updateDetailClass(dtlClass, () -> validateUpdate(dtlClass));
	}
	
	private void validateUpdate(DetailClasses dtlClass) throws Exception {
		if (dtlClass.getStartDate() == null) {
			throw new Exception("Tanggal mulai detail kelas tidak boleh kosong!");
		}
		if (dtlClass.getEndDate() == null) {
			throw new Exception("Tanggal akhir detail kelas tidak boleh kosong!");
		} else {
			if(dtlClass.getEndDate().compareTo(dtlClass.getStartDate()) < 0) {
				throw new Exception("Tanggal akhir detail kelas tidak boleh kurang dari tanggal mulai");
			}
		}
		if (dtlClass.getStartTime() == null) {
			throw new Exception("Waktu mulai detail kelas tidak boleh kosong!");
		}
		if (dtlClass.getEndTime() == null) {
			throw new Exception("Waktu akhir detail kelas tidak boleh kosong!");
		} else {
			if(dtlClass.getEndTime().compareTo(dtlClass.getStartTime()) < 0) {
				throw new Exception("Waktu akhir detail kelas tidak boleh kurang dari waktu akhir");
			}
		}
	}
	
	@Override
	public List<DetailClasses> getAllInactiveDetailClass() throws Exception {
		return detailClassesDao.getAllInactiveDetailClass();
	}
}