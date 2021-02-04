package com.lawencon.elearning.helper;

import com.lawencon.elearning.model.DetailModuleRegistrations;

import lombok.Data;

/**
 @author Nur Alfilail
*/

@Data
public class LearningMaterialsAndPermissions {
	
	DetailModuleRegistrations learningMaterial;
	private Boolean doesTutorPresent;
	private Boolean isUserOnTime;
	private Boolean isParticipantAccepted;
	
}
