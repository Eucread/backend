package com.eucread.eucread.util.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.eucread.eucread.exception.BusinessException;
import com.eucread.eucread.users.auth.CustomUserDetails;
import com.eucread.eucread.users.auth.exception.AuthErrorCode;
import com.eucread.eucread.users.entity.User;
import com.eucread.eucread.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

	private final UserRepository userRepository;

	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
			String email = userDetails.getEmail();

			return userRepository.findByEmail(email)
				.orElseThrow(() -> new BusinessException(AuthErrorCode.USER_NOT_EXIST));
		}

		throw new BusinessException(AuthErrorCode.USER_NOT_EXIST);
	}
}
