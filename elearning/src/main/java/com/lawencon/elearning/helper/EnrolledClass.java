package com.lawencon.elearning.helper;

import java.util.List;

import com.lawencon.elearning.model.DetailClasses;

import lombok.Data;

/**
 * @author Nur Alfilail
 */

@Data
public class EnrolledClass {

	private DetailClasses detailClass;
	private List<ModuleAndLearningMaterials> modulesAndMaterials;

}
