package com.eucread.eucread.users.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eucread.eucread.users.dto.request.EmailAuthReq;
import com.eucread.eucread.users.dto.request.RegisterReq;
import com.eucread.eucread.users.dto.response.MyInfoRes;
import com.eucread.eucread.users.service.EmailService;
import com.eucread.eucread.users.service.UserService;
import com.eucread.eucread.util.response.Response;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;
	private final EmailService emailService;

	/**
	 * 회원가입
	 * @param registerReq
	 * @return
	 */
	@PostMapping("/register")
	public ResponseEntity<Response<Void>> register(@Validated @RequestBody RegisterReq registerReq) {
		userService.register(registerReq);

		return Response.ok().toResponseEntity();
	}

	/**
	 * 이메일 중복확인
	 * @param email
	 * @return
	 */
	@GetMapping("/check-email")
	public ResponseEntity<Response<Void>> checkEmail(@RequestParam("email") String email) {
		userService.checkEmail(email);

		return Response.ok().toResponseEntity();
	}

	/**
	 * 닉네임 중복확인
	 * @param username
	 * @return
	 */
	@GetMapping("/check-username")
	public ResponseEntity<Response<Void>> checkUsername(@RequestParam("username") String username) {
		userService.checkUserName(username);

		return Response.ok().toResponseEntity();
	}

	@GetMapping("/auth/email")
	public ResponseEntity<Response<Void>> sendEmail(@RequestParam("email") String email) {
		emailService.sendEmail(email);

		return Response.ok().toResponseEntity();
	}

	@PostMapping("/auth/email")
	public ResponseEntity<Response<Void>> authEmail(@RequestBody EmailAuthReq request) {
		emailService.authEmail(request);

		return Response.ok().toResponseEntity();
	}

	/**
	 * 사용자 정보 조회
	 * @return
	 */
	@GetMapping("/info")
	public ResponseEntity<Response<MyInfoRes>> getMyInfo() {
		MyInfoRes myInfo = userService.getMyInfo();

		return Response.ok(myInfo).toResponseEntity();
	}

}
