package com.lawencon.elearning.constant;

public enum TemplateEmail {
	REGISTER("RGS"),
	RESET_PASSWORD("PWDRST"),
	ASSIGNMENT_SUBMISSION_PARTICIPANT("ASGPCP"),
	ASSIGNMENT_SUBMISSION_TUTOR("ASGTTR"),
	EVALUATION_PARTICIPANT("SCRUPD");
	
	public String code;
	
	TemplateEmail(String code){
		this.code = code;
	}
}
