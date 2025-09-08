package com.eucread.eucread.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.eucread.eucread.util.response.Response;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Response<Void>> handleCustomException(BusinessException ex) {
		Response<Void> response = Response.errorResponse(ex.getErrorCode());

		return response.toResponseEntity();
	}

	/**
	 * @Valid 어노테이션으로 인한 validation 예외 처리
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		
		Response<Map<String, String>> response = Response.errorResponse(
			HttpStatus.BAD_REQUEST.value(),
			"입력 데이터 검증에 실패했습니다",
			errors
		);
		
		return ResponseEntity.badRequest().body(response);
	}

	/**
	 * @Validated 어노테이션으로 인한 validation 예외 처리
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Response<Map<String, String>>> handleConstraintViolationException(ConstraintViolationException ex) {
		Map<String, String> errors = new HashMap<>();
		
		ex.getConstraintViolations().forEach((violation) -> {
			String fieldName = violation.getPropertyPath().toString();
			String errorMessage = violation.getMessage();
			errors.put(fieldName, errorMessage);
		});
		
		Response<Map<String, String>> response = Response.errorResponse(
			HttpStatus.BAD_REQUEST.value(),
			"입력 데이터 검증에 실패했습니다",
			errors);
		
		return ResponseEntity.badRequest().body(response);
	}

	/**
	 * 일반적인 validation 예외 처리
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Response<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
		Response<String> response = Response.errorResponse(
			HttpStatus.BAD_REQUEST.value(),
			"잘못된 입력값입니다",
			ex.getMessage()
		);
		
		return ResponseEntity.badRequest().body(response);
	}
}
