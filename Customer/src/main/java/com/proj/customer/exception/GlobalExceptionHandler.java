package com.proj.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.proj.customer.util.ResponseStructure;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseStructure<Exception>> handlesExceptions(Exception ex) {
		ResponseStructure<Exception> response = ResponseStructure.buildResponse(null, ex.getMessage(),
				HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ResponseStructure<Exception>>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CustomerNotFound.class)
	public ResponseEntity<ResponseStructure<CustomerNotFound>> customerNotFound(CustomerNotFound ex){
		ResponseStructure<CustomerNotFound> response = ResponseStructure.buildResponse(null,
				ex.getMessage(),HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
		return  new ResponseEntity<ResponseStructure<CustomerNotFound>>(response,HttpStatus.OK);
	}

}
