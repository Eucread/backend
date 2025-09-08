package com.eucread.eucread.admin.exception;

import org.springframework.http.HttpStatus;

import com.eucread.eucread.util.response.ErrorCode;

import lombok.Getter;

@Getter
public enum AdminErrorCode implements ErrorCode {

	CANNOT_READ_WHITELIST("ADM-001", HttpStatus.INTERNAL_SERVER_ERROR, "Admin whitelist 파일을 읽을 수 없습니다."),
	;

	private final String code;
	private final HttpStatus httpStatus;
	private final String message;

	AdminErrorCode(String code, HttpStatus httpStatus, String message) {
		this.code = code;
		this.httpStatus = httpStatus;
		this.message = message;
	}
}
