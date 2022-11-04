package com.reward.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCallException extends Exception {

	private static final long serialVersionUID = 1L;

	private HttpStatus statusCode;

	public CustomerCallException() {
		super();
	}

	public CustomerCallException(HttpStatus code, String message) {
		super(message);
		this.statusCode = code;
	}

}
