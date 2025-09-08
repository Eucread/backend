package com.eucread.eucread.users.auth.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eucread.eucread.exception.BusinessException;
import com.eucread.eucread.users.auth.CustomUserDetails;
import com.eucread.eucread.users.auth.dto.LoginDto;
import com.eucread.eucread.users.auth.exception.AuthErrorCode;
import com.eucread.eucread.users.entity.User;
import com.eucread.eucread.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmailFetchRoles(email)
			.orElseThrow(() -> new BusinessException(AuthErrorCode.USER_NOT_EXIST));

		List<String> roles = user.getUserRoles().stream()
			.map(userRole -> userRole.getRole().name())
			.toList();

		LoginDto loginDto = LoginDto.builder()
			.email(user.getEmail())
			.password(user.getPassword())
			.roles(roles)
			.build();

		return CustomUserDetails.create(loginDto);
	}
}
