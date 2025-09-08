package com.eucread.eucread.users.auth.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eucread.eucread.users.auth.CustomUserDetails;
import com.eucread.eucread.users.auth.exception.AuthErrorCode;
import com.eucread.eucread.util.jwt.JwtUtil;
import com.eucread.eucread.util.response.Response;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private static final List<String> WHITELIST = List.of(
		"/api/user/login",
		"/api/user/register",
		"/api/user/check-email",
		"/api/user/check-username"
	);

	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		log.info("Request URI: {}", request.getRequestURI());
		if (WHITELIST.contains(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}

		if (!processTokenAuthentication(request, response)) {
			return;
		}

		filterChain.doFilter(request, response);
	}

	private boolean processTokenAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		IOException {
		String accessToken = resolveToken(request);

		if (accessToken == null) {
			sendUnauthorizedResponse(response);
			return false;
		}

		try {
			if (jwtUtil.validateToken(accessToken)) {
				Claims claims = jwtUtil.getClaims(accessToken);

				CustomUserDetails userDetails = CustomUserDetails.createCustomUserDetailsFromClaims(
					claims);
				setUserAuthentication(userDetails);
			} else {
				sendTokenRefreshResponse(response);
				return false;
			}
		} catch (Exception e) {
			sendTokenRefreshResponse(response);
			return false;
		}
		return true;
	}

	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	private void setUserAuthentication(CustomUserDetails userDetails) {
		UsernamePasswordAuthenticationToken authentication =
			new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private void sendUnauthorizedResponse(HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		Response<Void> errorResponse = Response.errorResponse(AuthErrorCode.UNAUTHORIZED);
		response.getWriter().write(errorResponse.convertToJson());
	}

	private void sendTokenRefreshResponse(HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		Response<Void> errorResponse = Response.errorResponse(AuthErrorCode.INVALID_ACCESS_TOKEN);
		response.getWriter().write(errorResponse.convertToJson());
	}
}
