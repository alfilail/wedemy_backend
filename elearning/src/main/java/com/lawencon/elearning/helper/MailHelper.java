package com.lawencon.elearning.helper;

import lombok.Data;

@Data
public class MailHelper {
	private String from;
	private String to;
	private String subject;
	private String text;
}
