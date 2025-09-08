package com.eucread.eucread.users.auth;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.eucread.eucread.users.auth.dto.LoginDto;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

	private final LoginDto loginDto;

	public static CustomUserDetails create(LoginDto loginDto) {
		return new CustomUserDetails(loginDto);
	}

	public static CustomUserDetails createCustomUserDetailsFromClaims(Claims claims) {
		String email = claims.getSubject();

		List<?> rawRoles = claims.get("roles", List.class);
		List<String> roles = rawRoles.stream()
			.map(Object::toString)
			.toList();

		LoginDto loginDto = LoginDto.builder()
			.email(email)
			.password("")
			.roles(roles)
			.build();

		return create(loginDto);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return loginDto.getRoles().stream()
			.map(role -> new SimpleGrantedAuthority("ROLE_" + role))
			.toList();
	}

	@Override
	public String getPassword() {
		return loginDto.getPassword();
	}

	@Override
	public String getUsername() {
		return loginDto.getEmail();
	}

	public String getEmail() {
		return loginDto.getEmail();
	}
}
