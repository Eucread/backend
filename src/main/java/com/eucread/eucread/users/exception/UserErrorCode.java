package com.eucread.eucread.users.exception;

import org.springframework.http.HttpStatus;

import com.eucread.eucread.util.response.ErrorCode;

import lombok.Getter;

@Getter
public enum UserErrorCode implements ErrorCode {
    
    // 회원가입 관련 에러
    EMAIL_ALREADY_EXISTS("USER-001", HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다"),
    USERNAME_ALREADY_EXISTS("USER-002", HttpStatus.CONFLICT, "이미 사용 중인 사용자명입니다"),
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

