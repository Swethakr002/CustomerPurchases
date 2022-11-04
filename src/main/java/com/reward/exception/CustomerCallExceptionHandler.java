package com.reward.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomerCallExceptionHandler {

	@ExceptionHandler(value = { CustomerCallException.class })
	public ResponseEntity<Object> handleAPIRequestException(CustomerCallException rewardsException) {
		HttpStatus httpStatus = rewardsException.getStatusCode();
		CustomerCallException exception = new CustomerCallException(httpStatus, rewardsException.getMessage());
		return ResponseEntity.status(httpStatus).body(exception.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleConstraintViolationExceptions(MethodArgumentNotValidException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input parameters:");
	}

}
