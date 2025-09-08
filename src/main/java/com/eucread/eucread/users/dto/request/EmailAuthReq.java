package com.eucread.eucread.users.dto.request;

import lombok.Getter;

@Getter
public class EmailAuthReq {

	private String email;
	private String code;
}
