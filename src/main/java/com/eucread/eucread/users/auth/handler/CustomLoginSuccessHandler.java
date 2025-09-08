package com.eucread.eucread.users.auth.handler;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.eucread.eucread.users.auth.CustomUserDetails;
import com.eucread.eucread.util.jwt.JwtUtil;
import com.eucread.eucread.util.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	private final JwtUtil jwtUtil;
	private final ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		String accessToken = jwtUtil.generateAccessToken(userDetails);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		Map<String, String> data = Map.of("accessToken", accessToken);

		Response<Map<String, String>> successResponse = Response.ok(data);

		response.setStatus(HttpStatus.OK.value());
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(successResponse));
	}
}
