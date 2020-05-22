package com.example.demo.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController //because it is providing a response back in case of exceptions
@ControllerAdvice //i want this to be applicable to all other controllers 
public class CustomizedResponseEntityExceptionHandler
extends ResponseEntityExceptionHandler{
	
	//this method is handling exceptions from Exception class. 
	//So whenever an exception happens we want to return an instance of our bean -> ExceptionResponse
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//we created a new bean according to the structure we defined earlier, 
	//and returned a new responseEntity which was already in the method definition by passing the response and
	//the status code of internal server error for all exceptions right now.
	//we overrode the original responseEntity structure of the exceptions.
}
