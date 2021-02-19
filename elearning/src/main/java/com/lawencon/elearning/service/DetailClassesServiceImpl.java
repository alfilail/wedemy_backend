package com.lawencon.elearning.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.constant.TransactionNumberCode;
import com.lawencon.elearning.dao.DetailClassesDao;
import com.lawencon.elearning.helper.ClassInput;
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
		detailClassesDao.insert(detailClass, () -> validate(detailClass));
	}

	@Override
	public void update(DetailClasses dtlClass) throws Exception {
		detailClassesDao.update(dtlClass, () -> {
			validate(dtlClass);
			validateUpdate(dtlClass);
		});
	}

	@Override
	public void deleteById(String id, String idUser) throws Exception {
		detailClassesDao.deleteDtlClassById(id, idUser);
	}

	@Override
	public void reactiveOldClass(DetailClasses detailClass) throws Exception {
		try {
			begin();
			// ambil id class berdasarkan id
			Classes clazz = classService.getInActiveById(detailClass.getIdClass().getId());
			// update classes is active = true
			classService.reactivate(detailClass.getIdClass().getId(), detailClass.getCreatedBy());
			
			//ngeset yg tdk ada front end
			detailClass.setCode(generateCodeDetailClass(clazz.getCode(), detailClass.getStartDate()));
			detailClass.setViews(0);
			detailClass.setIdClass(clazz);
			
			System.out.println("iniii"+detailClass.getIdClass().getId());

			DetailClasses detailClassOld = detailClassesDao.getDtlClassByIdClass(detailClass.getIdClass().getId());
			
			System.out.println("cekkk id detail class" + detailClassOld.getId());
			
			List<ModuleRegistrations> modulesRegistrationListOld = moduleRegistrationsService
					.getAllByIdDtlClass(detailClassOld.getId());
			List<Modules> modulesList = new ArrayList<Modules>();

			List<DetailModuleRegistrations> detailModuleList = new ArrayList<DetailModuleRegistrations>();

			for (ModuleRegistrations moduleRegistration : modulesRegistrationListOld) {
				System.out.println("Test id module rgs " + moduleRegistration.getId());
				Modules module = modulesService.getById(moduleRegistration.getIdModule().getId());
				modulesList.add(module);
				
				List<DetailModuleRegistrations> detailModuleRegis = detailModuleRegistrationsService
						.getAllByIdModuleRgs(moduleRegistration.getId());
				
				System.out.println("cek module rgs : "+ detailModuleRegis.size());
				
				for (DetailModuleRegistrations detailModule : detailModuleRegis) {
					System.out.println("astaga" + detailModule.getIdModuleRegistration().getId());
					
					DetailModuleRegistrations detail = new DetailModuleRegistrations();
					detail.setIdLearningMaterial(detailModule.getIdLearningMaterial());
					
					System.out.println("ini cekkkk" + detailModule.getIdModuleRegistration());
					
					detail.setIdModuleRegistration(detailModule.getIdModuleRegistration());
					detail.setOrderNumber(detailModule.getOrderNumber());
					detail.setScheduleDate(detailModule.getScheduleDate());
					detailModuleList.add(detail);
				}
			}
			detailClassesDao.insert(detailClass, () -> validateReactive(detailClass));

//			ClassInput clazzHelper = new ClassInput();
//			clazzHelper.setClazz(clazz);
//			clazzHelper.setDetailClass(detailClass);
//			clazzHelper.setModule(modulesList);
			
			List<ModuleRegistrations> modulesRegistrationNew = new ArrayList<ModuleRegistrations>();
			for(Modules module : modulesList) {
				ModuleRegistrations moduleRgs = new ModuleRegistrations();
				moduleRgs.setIdDetailClass(detailClass);
				moduleRgs.setIdModule(module);
				moduleRegistrationsService.reactive(moduleRgs);
				modulesRegistrationNew.add(moduleRgs);
			}
			
			for(DetailModuleRegistrations detailModuleRgs : detailModuleList) {
				for(ModuleRegistrations md : modulesRegistrationNew) {
					if(detailModuleRgs.getIdModuleRegistration().getIdModule().getId()
							.equalsIgnoreCase(md.getIdModule().getId())) {
						detailModuleRgs.setIdModuleRegistration(md);
						detailModuleRegistrationsService.insert(detailModuleRgs);
					}
				}
			}
//			int i = 0;
//			for (int i = 1 ; i < detailModuleList.size() ; i++) {
//				if(detailModuleList.get(i).getIdModuleRegistration() == detailModuleList.get(i+1).getIdModuleRegistration()) {
//					dm.setIdModuleRegistration(modulesRegistrationNew.get(i));
//					detailModuleRegistrationsService.insert(dm);
//				}
//				else {
//					dm.setIdModuleRegistration(modulesRegistrationNew.get(i));
//					detailModuleRegistrationsService.insert(dm);
//				}
//				i++;
//				DetailModuleRegistrations dm = detailModuleList.get(i-1);
//				if(detailModuleList.get(i-1).getIdModuleRegistration().getId()
//						.equalsIgnoreCase(detailModuleList.get(i).getIdModuleRegistration().getId())) {
//					dm.setIdModuleRegistration(modulesRegistrationNew.get(i-1));
//					detailModuleRegistrationsService.insert(dm);
//				}
//				else {
//					dm.setIdModuleRegistration(modulesRegistrationNew.get(i));
//					detailModuleRegistrationsService.insert(dm);
//				}
//			}
			
			
//			detailModuleList.stream().filter(val -> {
//				val.getIdModuleRegistration().getId()
//			})
			
//			for (DetailModuleRegistrations dm : detailModuleList) {
//				dm.setIdModuleRegistration(moduleRgs);
//				detailModuleRegistrationsService.insert(dm);
//			}
//			System.out.println("size nya" + detailModuleList.size());
//			for (DetailModuleRegistrations dm : detailModuleList) {
//				dm.setIdModuleRegistration(moduleRgs);
//				detailModuleRegistrationsService.insert(dm);
//			}
			commit();
		} catch (Exception e) {
			rollback();
			throw new Exception(e);
		}
	}

	@Override
	public DetailClasses getById(String id) throws Exception {
		return detailClassesDao.getDtlClassById(id);
	}

	@Override
	public DetailClassInformation getInformationByIdDetailClass(String idDtlClass) throws Exception {
		DetailClassInformation dtlClassInfo = new DetailClassInformation();
		try {
			begin();
			updateViews(idDtlClass);
			dtlClassInfo.setDetailClass(detailClassesDao.getDtlClassById(idDtlClass));
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
		return detailClassesDao.getDtlClassByCode(code);
	}

	@Override
	public List<DetailClasses> getAll() throws Exception {
		return detailClassesDao.getAllDtlClasses();
	}

	@Override
	public List<DetailClasses> getAllByIdClass(String idClass) throws Exception {
		return detailClassesDao.getAllByIdClass(idClass);
	}

	@Override
	public List<DetailClasses> getAllInactive() throws Exception {
		return detailClassesDao.getAllInactive();
	}

	@Override
	public List<DetailClasses> getTutorClasses(String idTutor) throws Exception {
		return detailClassesDao.getAllByIdTutor(idTutor);
	}

	@Override
	public List<DetailClasses> getPopularClasses() throws Exception {
		return detailClassesDao.getPopularClasses();
	}

	private void validate(DetailClasses detailClass) throws Exception {
		if (detailClass.getStartDate() == null) {
			throw new Exception("Tanggal mulai detail kelas tidak boleh kosong!");
		} else {
			if(detailClass.getStartDate().isBefore(LocalDate.now())) {
				throw new Exception("Tanggal mulai tidak boleh sebelum hari ini");
			}
		}
		if (detailClass.getEndDate() == null) {
			throw new Exception("Tanggal akhir detail kelas tidak boleh kosong!");
		} else {
			if(detailClass.getEndDate().isBefore(detailClass.getStartDate())) {
				throw new Exception("Tanggal akhir tidak boleh sebelum tanggal mulai");
			}
		}
		if (detailClass.getStartTime() == null) {
			throw new Exception("Waktu mulai detail kelas tidak boleh kosong!");
		}
		if (detailClass.getEndTime() == null) {
			throw new Exception("Waktu akhir detail kelas tidak boleh kosong!");
		}
	}

	private void validateReactive(DetailClasses detailClass) throws Exception {
		validate(detailClass);
		DetailClasses detailClazz = detailClassesDao.getDtlClassByIdClass(detailClass.getIdClass().getId());
		if (detailClass.getStartDate().compareTo(detailClazz.getEndDate()) < 0) {
			throw new Exception(
					"Tanggal mulai detail kelas tidak boleh kurang dari tanggal akhir detail kelas sebelumnya");
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

	private void validateUpdate(DetailClasses dtlClass) throws Exception {
		if (dtlClass.getEndDate() == null) {
			throw new Exception("Tanggal akhir detail kelas tidak boleh kosong!");
		} else {
			if (dtlClass.getEndDate().compareTo(dtlClass.getStartDate()) < 0) {
				throw new Exception("Tanggal akhir detail kelas tidak boleh kurang dari tanggal mulai");
			}
		}
		if (dtlClass.getEndTime() == null) {
			throw new Exception("Waktu akhir detail kelas tidak boleh kosong!");
		} else {
			if (dtlClass.getEndTime().compareTo(dtlClass.getStartTime()) < 0) {
				throw new Exception("Waktu akhir detail kelas tidak boleh kurang dari waktu akhir");
			}
		}
	}

}