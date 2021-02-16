package com.lawencon.elearning.constant;

/**
 * @author Nur Alfilail
 */

public enum RoleCode {

	ADMIN("ADM"), TUTOR("TTR"), PARTICIPANT("PCP");

	public String code;

	RoleCode(String code) {
		this.code = code;
	}

}
