package com.eucread.eucread.util.response;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

	String getCode();

	HttpStatus getHttpStatus();

	String getMessage();

}
