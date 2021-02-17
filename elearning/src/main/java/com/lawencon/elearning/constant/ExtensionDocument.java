package com.lawencon.elearning.constant;

public enum ExtensionDocument {
	
	PDF("pdf"), DOC("doc"), DOCX("docx"), WPS("wps"), ODT("odt"), PPT("ppt"), TXT("plain");

	public String code;

	private ExtensionDocument(String code) {
		this.code = code;
	}
}
