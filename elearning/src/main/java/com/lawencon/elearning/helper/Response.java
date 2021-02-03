package com.lawencon.elearning.helper;

import org.springframework.http.HttpStatus;

import com.lawencon.elearning.util.MessageStat;

import lombok.Data;

/**
 * @author Nur Alfilail
 */

@Data
public class Response<T> {

	private Boolean ok;
	private HttpStatus status;
	private MessageStat message;
	private T data;

	public Response(Boolean ok, HttpStatus status, MessageStat message, T data) {
		this.ok = ok;
		this.status = status;
		this.message = message;
		this.data = data;
	}

}
