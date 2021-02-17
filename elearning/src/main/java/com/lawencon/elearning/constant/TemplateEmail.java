package com.lawencon.elearning.constant;

public enum TemplateEmail {
	REGISTER("RGS", "Registrasi akun Wedemy sukses"),
	RESET_PASSWORD("PWDRST", "Password telah diganti"),
	ASSIGNMENT_SUBMISSION_PARTICIPANT("ASGPCP", "Tugas telah terkirim"),
	ASSIGNMENT_SUBMISSION_TUTOR("ASGTTR", "Tugas telah dikirim"),
	EVALUATION_PARTICIPANT("SCRUPD", "Nilai tugas telah di nilai");
	
	public String code;
	public String subject;
	
	TemplateEmail(String code, String subject){
		this.code = code;
		this.subject = subject;
	}
}
