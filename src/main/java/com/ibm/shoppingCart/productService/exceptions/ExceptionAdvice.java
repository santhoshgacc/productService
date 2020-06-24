package com.ibm.shoppingCart.productService.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(value = InvalidRequestException.class)
	public ResponseEntity<CustomErrorResponse> handleGenericNotFoundException(InvalidRequestException e) {
		CustomErrorResponse error = new CustomErrorResponse("BAD_REQUEST_ERROR", e.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus((HttpStatus.BAD_REQUEST.value()));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
}
