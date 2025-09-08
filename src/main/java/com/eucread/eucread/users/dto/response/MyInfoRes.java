package com.eucread.eucread.users.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyInfoRes {

	private String email;
	private String username;
	private String status;
	private List<String> role;
	private String registerDate;
}
