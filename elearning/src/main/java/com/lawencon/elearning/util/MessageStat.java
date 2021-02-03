package com.lawencon.elearning.util;

public enum MessageStat {
	
	SUCCESS_CREATED("Successfully Create Data"), SUCCESS_UPDATE("Successfully Update Data"), SUCCESS_DELETE("Successfully Delete Data"),
	SUCCESS_RETRIEVE("Successfully Retrieve Data"), FAILED("Failed");
	
	public String msg;
	
	MessageStat(String msg) {
		this.msg = msg;
	}

}