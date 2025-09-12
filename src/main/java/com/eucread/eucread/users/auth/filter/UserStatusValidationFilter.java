package com.eucread.eucread.users.auth.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eucread.eucread.users.auth.exception.AuthErrorCode;
import com.eucread.eucread.users.auth.service.CustomUserDetailsService;
import com.eucread.eucread.users.entity.User;
import com.eucread.eucread.users.enums.UserStatus;
import com.eucread.eucread.util.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserStatusValidationFilter extends OncePerRequestFilter {

	private final CustomUserDetailsService customUserDetailsService;
	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		if (!"/api/user/login".equals(request.getRequestURI()) || !"POST".equals(request.getMethod())) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			String email = request.getParameter("email");

			if (!StringUtils.hasText(email)) {
				filterChain.doFilter(request, response);
				return;
			}

			User user = customUserDetailsService.findUserByEmail(email);
			if (user != null) {
				UserStatus status = user.getStatus();

				if (status == UserStatus.DELETED) {
					log.warn("탈퇴한 사용자 로그인 시도: {}", email);
					sendErrorResponse(response, AuthErrorCode.DELETED_USER);
					return;
				} else if (status == UserStatus.BLOCKED) {
					log.warn("정지된 사용자 로그인 시도: {}", email);
					sendErrorResponse(response, AuthErrorCode.BLOCKED_USER);
					return;
				}
			}

			filterChain.doFilter(request, response);
		} catch (Exception e) {
			log.error("UserStatusValidationFilter 에러", e);
			filterChain.doFilter(request, response);
		}
	}

	private void sendErrorResponse(HttpServletResponse response, AuthErrorCode errorCode) throws IOException {

		Response<Object> errorResponse = Response.errorResponse(errorCode);

		response.setContentType("application/json;charset=UTF-8");
		String jsonResponse = objectMapper.writeValueAsString(errorResponse);
		response.getWriter().write(jsonResponse);
	}
}
