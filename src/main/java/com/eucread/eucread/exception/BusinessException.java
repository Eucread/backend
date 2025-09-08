package com.eucread.eucread.exception;

import com.eucread.eucread.util.response.ErrorCode;

import lombok.Builder;
import lombok.Getter;

public class BusinessException extends RuntimeException{

	@Getter
	private final ErrorCode errorCode;

	@Builder
	public BusinessException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
