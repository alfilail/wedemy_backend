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
import com.lawencon.elearning.helper.EnrolledClass;
import com.lawencon.elearning.helper.LearningMaterialsAndPermissions;
import com.lawencon.elearning.helper.ModuleAndLearningMaterials;
import com.lawencon.elearning.model.ApprovementsRenewal;
import com.lawencon.elearning.model.DetailModuleRegistrations;
import com.lawencon.elearning.model.ModuleRegistrations;
import com.lawencon.elearning.model.Modules;
import com.lawencon.elearning.model.Presences;
import com.lawencon.elearning.model.Users;
import com.lawencon.elearning.util.ApprovementCode;
import com.lawencon.elearning.util.RoleCode;
import com.lawencon.elearning.util.TransactionNumberCode;

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

	@Autowired
	private UsersService userService;

	@Override
	public void insertModuleRegistration(ClassesHelper clazzHelper) throws Exception {
		List<Modules> modulesList = clazzHelper.getModule();
		for (Modules modules : modulesList) {
			ModuleRegistrations moduleRegistrations = new ModuleRegistrations();
			moduleRegistrations.setTrxNumber(generateTrxNumber(TransactionNumberCode.MODULE_REGISTRATION.code));
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
	public EnrolledClass getModuleAndLearningMaterialsByIdDtlClass(String idUser, String idDtlClass) throws Exception {
		EnrolledClass enrolledClass = new EnrolledClass();
		enrolledClass.setDetailClass(detailClassService.getById(idDtlClass));
		LocalTime startTime = enrolledClass.getDetailClass().getStartTime();
		LocalTime endTime = enrolledClass.getDetailClass().getEndTime();
		List<ModuleAndLearningMaterials> listResult = new ArrayList<>();
		List<ModuleRegistrations> moduleRgsList = moduleRegistrationDao.getByIdDtlClass(idDtlClass);
		Users user = userService.getById(idUser);
		for (ModuleRegistrations moduleRgs : moduleRgsList) {
			ModuleAndLearningMaterials result = new ModuleAndLearningMaterials();
			List<LearningMaterialsAndPermissions> learningMaterials = new ArrayList<>();
			List<DetailModuleRegistrations> dtlModuleList = dtlModuleRgsService
					.getDetailModuleRegistrationsByIdModuleRgs(moduleRgs.getId());
			for (DetailModuleRegistrations dtlModule : dtlModuleList) {
				LearningMaterialsAndPermissions learningMaterial = new LearningMaterialsAndPermissions();
				learningMaterial.setLearningMaterial(dtlModule);
				Presences tutorPresent = presenceService
						.doesTutorPresent(learningMaterial.getLearningMaterial().getId());
				Presences participantPresent = presenceService
						.doesParticipantPresent(learningMaterial.getLearningMaterial().getId(), idUser);
				ApprovementsRenewal participantApprovement = approvementRenewalService
						.checkParticipantPresence(learningMaterial.getLearningMaterial().getId(), idUser);
				if (tutorPresent != null) {
					learningMaterial.setDoesTutorPresent(true);
				} else {
					learningMaterial.setDoesTutorPresent(false);
				}
				if (LocalDate.now().isEqual(dtlModule.getScheduleDate()) && LocalTime.now().isAfter(startTime)
						&& LocalTime.now().isBefore(endTime)) {
					learningMaterial.setIsUserOnTime(true);
				} else {
					learningMaterial.setIsUserOnTime(false);
				}
				if (user.getIdRole().getCode().equals(RoleCode.PARTICIPANT.code)) {
					if (participantPresent != null) {
						learningMaterial.setDoesParticipantPresent(true);
					} else {
						learningMaterial.setDoesParticipantPresent(false);
					}
					if (participantApprovement != null) {
						if (participantApprovement.getIdApprovement().getCode().equals(ApprovementCode.PENDING.code)) {
							learningMaterial.setIsParticipantConfirmed(false);
							learningMaterial.setIsParticipantAccepted(false);
						} else if (participantApprovement.getIdApprovement().getCode()
								.equals(ApprovementCode.ACCEPTED.code)) {
							learningMaterial.setIsParticipantConfirmed(true);
							learningMaterial.setIsParticipantAccepted(true);
						} else if (participantApprovement.getIdApprovement().getCode()
								.equals(ApprovementCode.REJECTED.code)) {
							learningMaterial.setIsParticipantConfirmed(true);
							learningMaterial.setIsParticipantAccepted(false);
						}
					} else {
						learningMaterial.setIsParticipantConfirmed(false);
						learningMaterial.setIsParticipantAccepted(false);
					}
				}
				learningMaterials.add(learningMaterial);
			}
			result.setModule(moduleRgs);
			result.setLearningMaterials(learningMaterials);
			listResult.add(result);
		}
		enrolledClass.setModulesAndMaterials(listResult);
		return enrolledClass;
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


}
