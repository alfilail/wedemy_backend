package com.lawencon.elearning.helper;

import com.lawencon.elearning.model.DetailModuleRegistrations;
import com.lawencon.elearning.model.LearningMaterials;
import com.lawencon.elearning.model.ModuleRegistrations;

import lombok.Data;

@Data
public class LearningMaterialsHelper {

	private LearningMaterials learningMaterial;
	private ModuleRegistrations moduleRegistration;
	private DetailModuleRegistrations dtlModuleRegistration;

}