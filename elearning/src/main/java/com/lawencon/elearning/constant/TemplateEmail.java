package com.lawencon.elearning.constant;

public enum TemplateEmail {
	REGISTER("RGS", "Registrasi akun Wedemy sukses"),
	RESET_PASSWORD("PWDRST", "Password telah diganti"),
	ASSIGNMENT_SUBMISSION_PARTICIPANT("ASGPCP", ""),
	ASSIGNMENT_SUBMISSION_TUTOR("ASGTTR", ""),
	EVALUATION_PARTICIPANT("SCRUPD", "Nilai tugas telah di nilai");
	
	public String code;
	public String subject;
	
	TemplateEmail(String code, String subject){
		this.code = code;
		this.subject = subject;
	}
}
