package com.lawencon.elearning.helper;

import org.springframework.http.HttpHeaders;

import lombok.Data;

@Data
public class HttpResponse<T> {
	private Boolean ok;
	private String status;
	private String message;
	private byte[] out;
	private HttpHeaders headers;
	private T data;
	
	public HttpResponse(Boolean ok, String status, String message, byte[] out, HttpHeaders headers, T data) {
		this.ok = ok;
		this.status = status;
		this.message = message;
		this.out = out;
		this.headers = headers;
		this.data = data;
	}
}
