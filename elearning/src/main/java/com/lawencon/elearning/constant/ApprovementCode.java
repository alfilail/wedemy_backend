package com.lawencon.elearning.constant;

/**
 * @author Nur Alfilail
 */

public enum ApprovementCode {

	PENDING("PND"), ACCEPTED("ACC"), REJECTED("RJC");

	public String code;

	ApprovementCode(String code) {
		this.code = code;
	}

}
