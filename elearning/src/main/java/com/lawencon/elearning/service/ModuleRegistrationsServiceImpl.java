package com.lawencon.elearning.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.ModuleRegistrationsDao;
import com.lawencon.elearning.helper.ClassesHelper;
import com.lawencon.elearning.helper.LearningMaterialsAndPermissions;
import com.lawencon.elearning.helper.ModuleAndLearningMaterials;
import com.lawencon.elearning.model.ApprovementsRenewal;
import com.lawencon.elearning.model.DetailModuleRegistrations;
import com.lawencon.elearning.model.ModuleRegistrations;
import com.lawencon.elearning.model.Modules;
import com.lawencon.elearning.model.Presences;

@Service
public class ModuleRegistrationsServiceImpl extends ElearningBaseServiceImpl implements ModuleRegistrationsService {

	@Autowired
	private ModuleRegistrationsDao moduleRegistrationDao;

	@Autowired
	private DetailModuleRegistrationsService dtlModuleRgsService;

	@Autowired
	private ModulesService modulesService;

	@Autowired
	private DetailClassesService detailClassService;

	@Autowired
	private ApprovementsRenewalService approvementRenewalService;

	@Autowired
	private PresencesService presenceService;

	@Override
	public void insertModuleRegistration(ClassesHelper clazzHelper) throws Exception {
		List<Modules> modulesList = clazzHelper.getModule();
		for (Modules modules : modulesList) {
			ModuleRegistrations moduleRegistrations = new ModuleRegistrations();
			moduleRegistrations.setTrxNumber(generateTrxNumber());
			moduleRegistrations.setIdDetailClass(clazzHelper.getDetailClass());
			moduleRegistrations.setIdModule(modules);
			moduleRegistrationDao.insertModuleRegistration(moduleRegistrations,
					() -> validateInsert(moduleRegistrations));
		}
	}

	@Override
	public ModuleRegistrations getByIdDetailClassAndIdModuleRegistration(String idDtlClass, String idModRegist)
			throws Exception {
		return moduleRegistrationDao.getByIdDetailClassAndIdModuleRegistration(idDtlClass, idModRegist);
	}

	@Override
	public List<ModuleRegistrations> getByIdDtlClass(String idClass) throws Exception {
		return moduleRegistrationDao.getByIdDtlClass(idClass);
	}

	@Override
	public List<ModuleAndLearningMaterials> getModuleAndLearningMaterialsByIdDtlClass(String idUser, String idDtlClass)
			throws Exception {
		List<ModuleAndLearningMaterials> listResult = new ArrayList<>();
		List<ModuleRegistrations> moduleRgsList = moduleRegistrationDao.getByIdDtlClass(idDtlClass);
		for (ModuleRegistrations moduleRgs : moduleRgsList) {
			LocalTime startTime = moduleRgs.getIdDetailClass().getStartTime();
			ModuleAndLearningMaterials result = new ModuleAndLearningMaterials();
			List<LearningMaterialsAndPermissions> learningMaterials = new ArrayList<>();
			List<DetailModuleRegistrations> dtlModuleList = dtlModuleRgsService
					.getDetailModuleRegistrationsByIdModuleRgs(moduleRgs.getId());
			for (DetailModuleRegistrations dtlModule : dtlModuleList) {
				LearningMaterialsAndPermissions learningMaterial = new LearningMaterialsAndPermissions();
				learningMaterial.setLearningMaterial(dtlModule);
				Presences tutorPresence = presenceService
						.doesTutorPresent(learningMaterial.getLearningMaterial().getId());
				ApprovementsRenewal participantPresence = approvementRenewalService
						.checkParticipantPresence(learningMaterial.getLearningMaterial().getId(), idUser);
				if (tutorPresence != null) {
					learningMaterial.setDoesTutorPresent(true);
				} else {
					learningMaterial.setDoesTutorPresent(false);
				}
				if (participantPresence != null && participantPresence.getIdApprovement().getCode() == "ACC") {
					learningMaterial.setIsParticipantAccepted(true);
				} else {
					learningMaterial.setIsParticipantAccepted(false);
				}
				if (LocalDate.now().isEqual(dtlModule.getScheduleDate()) && LocalTime.now().isBefore(startTime)) {
					learningMaterial.setIsUserOnTime(true);
				} else {
					learningMaterial.setIsUserOnTime(false);
				}
				learningMaterials.add(learningMaterial);
			}
			result.setModule(moduleRgs);
			result.setLearningMaterials(learningMaterials);
			listResult.add(result);
		}
		return listResult;
	}
	
	@Override
	public List<ModuleRegistrations> getModuleRegistrationsByIdDetailClass(String idDetailClass) throws Exception {
		return moduleRegistrationDao.getIdModuleRegistrationByIdDetailClass(idDetailClass);
	}

	private void validateInsert(ModuleRegistrations moduleRegistration) throws Exception {
//		if (moduleRegistration.getIdModule() == null) {
//			throw new Exception("Module tidak boleh kosong!");
//		} else {
//			if (moduleRegistration.getIdModule().getId() == null
//					|| moduleRegistration.getIdModule().getId().equals("")) {
//				throw new Exception("Id Module tidak boleh kosong!");
//			} else {
//				Modules module = modulesService.getModuleById(moduleRegistration.getIdModule().getId());
//				System.out.println("Test " + moduleRegistration.getIdModule().getId());
//				System.out.println("Testtt" + module);
//				if (module == null) {
//					throw new Exception("Module tidak ada!");
//				} else {
//					if (moduleRegistration.getIdDetailClass() == null) {
//						throw new Exception("Detail kelas tidak boleh kosong!");
//					} else {
//						if (moduleRegistration.getIdDetailClass().getId() == null) {
//							throw new Exception("Id Detail Class tidak boleh kosong!");
//						} else {
//							DetailClasses dtlClazz = detailClassService
//									.getDetailClassById(moduleRegistration.getIdDetailClass().getId());
//							if (dtlClazz == null) {
//								throw new Exception("Detail Class tidak ada!");
//							}
//						}
//					}
//				}
//			}
//		}
	}

	private String generateTrxNumber() {
		Random random = new Random();
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yy-MM-dd");
		String formattedDate = localDate.format(myFormat);
		String trxCodeValue = String.valueOf(random.nextInt((999 + 1 - 100) + 100));
		String trx = bBuilder(formattedDate).toString();
		trx = trx.replaceAll("-", "");
		String trxNumber = bBuilder("MRG-", trx, "-", trxCodeValue).toString();
		return trxNumber;
	}

}
