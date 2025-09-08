package com.eucread.eucread.users.dto.request;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterReq {

	@NotBlank(message = "이메일은 필수입니다")
	@Email(message = "올바른 이메일 형식이 아닙니다")
	private String email;

	@NotBlank(message = "비밀번호는 필수입니다")
	@Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).*$",
			 message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다")
	private String password;

	@NotBlank(message = "사용자명은 필수입니다")
	@Size(min = 2, max = 20, message = "사용자명은 2자 이상 20자 이하여야 합니다")
	@Pattern(regexp = "^[a-zA-Z0-9가-힣_]+$", message = "사용자명은 영문, 숫자, 한글, 언더스코어만 사용 가능합니다")
	private String username;

	@Size(min = 1, message = "역할은 최소 1개 이상 선택해야 합니다")
	private List<
		@Pattern(
			regexp = "^(READER|CREATOR|TRANSLATOR|ADMIN)$",
			message = "역할은 READER, CREATOR, TRANSLATOR, ADMIN 중 하나여야 합니다"
		)
			String> role;

}
