package com.lawencon.elearning.util;

public enum GeneralUtil {
	REGISTER("rgs"),
	RESET_PASSWORD("pwdrst"),
	ASSIGNMENT_SUBMISSION_PARTICIPANT("asgpcp"),
	ASSIGNMENT_SUBMISSION_TUTOR("asgttr"),
	EVALUATION_PARTICIPANT("scrupd");
	
	public String code;
	
	GeneralUtil(String code){
		this.code = code;
	}
}
