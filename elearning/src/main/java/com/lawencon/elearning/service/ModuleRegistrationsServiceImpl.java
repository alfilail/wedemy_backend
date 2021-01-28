package com.lawencon.elearning.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.elearning.dao.ModuleRegistrationsDao;
import com.lawencon.elearning.helper.ClassesHelper;
import com.lawencon.elearning.helper.ModuleAndLearningMaterials;
import com.lawencon.elearning.model.ModuleRegistrations;
import com.lawencon.elearning.model.Modules;

@Service
public class ModuleRegistrationsServiceImpl extends ElearningBaseServiceImpl implements ModuleRegistrationsService {

	@Autowired
	private ModuleRegistrationsDao moduleRegistrationDao;

	@Autowired
	private DetailModuleRegistrationsService dtlModuleRgsService;

	@Override
	public void insertModuleRegistration(ClassesHelper clazzHelper) throws Exception {
		System.out.println("module : " + clazzHelper.getModule());
		Modules[] modulesList = clazzHelper.getModule();
		for (Modules modules : modulesList) {
			ModuleRegistrations moduleRegistrations = new ModuleRegistrations();
			moduleRegistrations.setCreatedBy(clazzHelper.getClazz().getCreatedBy());
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
	public List<ModuleAndLearningMaterials> getModuleAndLearningMaterialsByIdDtlClass(String idDtlClass)
			throws Exception {
		List<ModuleAndLearningMaterials> listResult = new ArrayList<>();
		List<ModuleRegistrations> moduleRgsList = moduleRegistrationDao.getByIdDtlClass(idDtlClass);
		for (ModuleRegistrations moduleRgs : moduleRgsList) {
			ModuleAndLearningMaterials result = new ModuleAndLearningMaterials();
			result.setModule(moduleRgs);
			result.setLearningMaterials(
					dtlModuleRgsService.getDetailModuleRegistrationsByIdModuleRgs(moduleRgs.getId()));
			listResult.add(result);
		}
		return listResult;
	}

	private void validateInsert(ModuleRegistrations moduleRegistration) {

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
