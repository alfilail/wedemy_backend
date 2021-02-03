package com.lawencon.elearning.helper;

import java.util.List;

import com.lawencon.elearning.model.DetailModuleRegistrations;
import com.lawencon.elearning.model.ModuleRegistrations;

import lombok.Data;

/**
 * @author Nur Alfilail
 */

@Data
public class ModuleAndLearningMaterials {

	private ModuleRegistrations module;
	private List<DetailModuleRegistrations> learningMaterials;
	
}
