package com.lawencon.elearning.util;

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
