package com.lawencon.elearning.util;

public enum GeneralUtil {
	REGISTER("RGS"),
	RESET_PASSWORD("PWDRST"),
	ASSIGNMENT_SUBMISSION_PARTICIPANT("ASGPCP"),
	ASSIGNMENT_SUBMISSION_TUTOR("ASGTTR"),
	EVALUATION_PARTICIPANT("SCRUPD");
	
	public String code;
	
	GeneralUtil(String code){
		this.code = code;
	}
}
