package com.xlens.mongodb.mongodb.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PersonExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> exceptionHandler(Exception ex) {
		Map<String, Object> error = new HashMap<>();
		error.put("code",HttpStatus.NOT_FOUND.value());
		error.put("errorMessage",ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
}
