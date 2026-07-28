package com.paymentledger.shared.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProblemDetailsExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
		throw new UnsupportedOperationException("TODO: map field errors -> ApiError, 400");
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ApiError> handleIllegalState(IllegalStateException ex) {
		throw new UnsupportedOperationException("TODO: map domain rule violations -> ApiError, 422");
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleUnexpected(Exception ex) {
		throw new UnsupportedOperationException("TODO: map unexpected failures -> ApiError, 500");
	}
}
