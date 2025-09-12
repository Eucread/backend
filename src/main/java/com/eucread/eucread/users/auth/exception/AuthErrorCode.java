package com.eucread.eucread.users.auth.exception;

import org.springframework.http.HttpStatus;

import com.eucread.eucread.util.response.ErrorCode;

import lombok.Getter;

@Getter
public enum AuthErrorCode implements ErrorCode {

	USER_NOT_EXIST("AUTH-001", HttpStatus.NOT_FOUND, "존재하지 않는 유저"),
	UNAUTHORIZED("AUTH-002", HttpStatus.UNAUTHORIZED, "인증되지않음"),
	INVALID_ACCESS_TOKEN("AUTH-003", HttpStatus.BAD_REQUEST, "액세스 토큰 오류"),
	AUTH_CODE_EXPIRED("AUTH-004", HttpStatus.NOT_FOUND, "이메일 인증 코드 만료"),
	AUTH_CODE_INVALID("AUTH-005", HttpStatus.BAD_REQUEST, "이메일 인증 코드 오류"),
	NO_ADMIN_PERMISSION_EMAIL("AUTH-006", HttpStatus.BAD_REQUEST, "ADMIN 가입 불가 이메일"),
	DELETED_USER("AUTH-007", HttpStatus.BAD_REQUEST, "탈퇴한 사용자"),
	BLOCKED_USER("AUTH-008", HttpStatus.BAD_REQUEST, "차단된 사용자"),
	LOGIN_FAILED("AUTH-009", HttpStatus.BAD_REQUEST, "로그인 인증 실패"),

	;

	private final String code;
	private final HttpStatus httpStatus;
	private final String message;

	AuthErrorCode(String code, HttpStatus httpStatus, String message) {
		this.code = code;
		this.httpStatus = httpStatus;
		this.message = message;
	}
}
