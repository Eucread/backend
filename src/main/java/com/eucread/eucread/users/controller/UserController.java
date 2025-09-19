package com.eucread.eucread.users.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eucread.eucread.users.dto.request.EditUserInfoReq;
import com.eucread.eucread.users.dto.request.EmailAuthReq;
import com.eucread.eucread.users.dto.request.RegisterReq;
import com.eucread.eucread.users.dto.response.MyInfoRes;
import com.eucread.eucread.users.exception.UserErrorCode;
import com.eucread.eucread.users.service.EmailService;
import com.eucread.eucread.users.service.UserService;
import com.eucread.eucread.util.response.Response;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

	private final UserService userService;
	private final EmailService emailService;

	/**
	 * 회원가입
	 * @param registerReq
	 * @return
	 */
	@PostMapping("/user/register")
	public ResponseEntity<Response<Void>> register(@Validated @RequestBody RegisterReq registerReq) {
		userService.register(registerReq);

		return Response.ok().toResponseEntity();
	}

	/**
	 * 닉네임 중복확인
	 * @param username
	 * @return
	 */
	@GetMapping("/user/check-username")
	public ResponseEntity<Response<Void>> checkUsername(@RequestParam("username") String username) {
		userService.checkUserName(username);

		return Response.ok().toResponseEntity();
	}

	@GetMapping("/user/auth/email")
	public ResponseEntity<Response<Void>> sendEmail(@RequestParam("email") String email) {

		userService.checkEmail(email);
		emailService.sendEmail(email);

		return Response.ok().toResponseEntity();
	}

	@PostMapping("/user/auth/email")
	public ResponseEntity<Response<String>> authEmail(@RequestBody EmailAuthReq request) {
		boolean result = emailService.authEmail(request);

		if (!result) {
			Response.errorResponse(400, "이메일 인증 실패").toResponseEntity();
		}

		return Response.ok("이메일 인증 성공").toResponseEntity();
	}

	/**
	 * 사용자 정보 조회
	 * @return
	 */
	@GetMapping("/auth/user/info")
	public ResponseEntity<Response<MyInfoRes>> getMyInfo() {
		MyInfoRes myInfo = userService.getMyInfo();

		return Response.ok(myInfo).toResponseEntity();
	}

	/**
	 * 사용자 정보 수정
	 * @param request
	 * @return
	 */
	@PatchMapping("/auth/user/info")
	public ResponseEntity<Response<Void>> editMyInfo(@RequestBody EditUserInfoReq request) {
		userService.editMyInfo(request);

		return Response.ok().toResponseEntity();
	}

	@DeleteMapping("/auth/user/exit")
	public ResponseEntity<Response<Void>> userExit() {
		userService.userExit();

		return Response.ok().toResponseEntity();
	}

}
