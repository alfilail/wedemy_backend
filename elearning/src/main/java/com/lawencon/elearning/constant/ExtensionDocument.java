package com.lawencon.elearning.constant;

public enum ExtensionDocument {
	PDF("pdf"), DOC("doc"), DOCX("docx"), WPS("wps"), ODT("odt");
	
	public String code;
	
	private ExtensionDocument(String code) {
		this.code = code;
	}
}
