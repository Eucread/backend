package com.eucread.eucread.users.exception;

import org.springframework.http.HttpStatus;

import com.eucread.eucread.util.response.ErrorCode;

import lombok.Getter;

@Getter
public enum UserErrorCode implements ErrorCode {
    
    // 회원가입 관련 에러
    EMAIL_ALREADY_EXISTS("USER-001", HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다"),
    USERNAME_ALREADY_EXISTS("USER-002", HttpStatus.CONFLICT, "이미 사용 중인 사용자명입니다"),
    EMAIL_AUTH_FAILED("USER-003", HttpStatus.BAD_REQUEST, "이메일 인증 실패"),
    EMAIL_AUTH_REQUIRED("USER-004", HttpStatus.BAD_REQUEST, "이메일 인증은 필수입니다."),
    DIFFERENT_EMAIL("USER-005", HttpStatus.BAD_REQUEST, "가입 요청한 이메일과 인증된 이메일이 다릅니다."),
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    UserErrorCode(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}



