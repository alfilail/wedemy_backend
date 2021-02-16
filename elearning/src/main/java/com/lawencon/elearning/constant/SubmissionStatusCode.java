package com.lawencon.elearning.constant;

/**
 * @author Nur Alfilail
 */

public enum SubmissionStatusCode {

	UPLOADED("UPL"), GRADED("GRD");

	public String code;

	SubmissionStatusCode(String code) {
		this.code = code;
	}
}
