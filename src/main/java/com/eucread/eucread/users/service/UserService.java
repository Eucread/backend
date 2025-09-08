package com.eucread.eucread.users.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eucread.eucread.admin.component.AdminWhitelist;
import com.eucread.eucread.exception.BusinessException;
import com.eucread.eucread.users.auth.exception.AuthErrorCode;
import com.eucread.eucread.users.dto.request.RegisterReq;
import com.eucread.eucread.users.dto.response.MyInfoRes;
import com.eucread.eucread.users.entity.User;
import com.eucread.eucread.users.entity.UserRole;
import com.eucread.eucread.users.enums.Role;
import com.eucread.eucread.users.enums.UserStatus;
import com.eucread.eucread.users.exception.UserErrorCode;
import com.eucread.eucread.users.repository.UserRepository;
import com.eucread.eucread.users.repository.UserRoleRepository;
import com.eucread.eucread.util.security.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final PasswordEncoder passwordEncoder;
	private final AdminWhitelist adminWhitelist;
	private final SecurityUtil securityUtil;

	@Transactional
	public void register(RegisterReq registerReq) {

		checkEmail(registerReq.getEmail());
		checkUserName(registerReq.getUsername());

		String encodedPassword = passwordEncoder.encode(registerReq.getPassword());

		User user = User.builder()
			.email(registerReq.getEmail())
			.password(encodedPassword)
			.username(registerReq.getUsername())
			.status(UserStatus.ACTIVE)
			.build();

		User savedUser = userRepository.save(user);

		List<Role> roles = registerReq.getRole().stream()
			.map(Role::valueOf)
			.toList();

		if (roles.contains(Role.ADMIN)) {
			if (!adminWhitelist.contains(registerReq.getEmail())) {
				throw new BusinessException(AuthErrorCode.NO_ADMIN_PERMISSION_EMAIL);
			}
		}

		for (Role role : roles) {
			UserRole userRole = UserRole.builder()
				.user(savedUser)
				.role(role)
				.build();

			userRoleRepository.save(userRole);
		}
	}

	public void checkEmail(String email) {
		if (userRepository.existsByEmail(email)) {
			throw new BusinessException(UserErrorCode.EMAIL_ALREADY_EXISTS);
		}
	}

	public void checkUserName(String username) {
		if (userRepository.existsByUsername(username)) {
			throw new BusinessException(UserErrorCode.USERNAME_ALREADY_EXISTS);
		}
	}

	public MyInfoRes getMyInfo() {
		User currentUser = securityUtil.getCurrentUser();

		return MyInfoRes.builder()
			.email(currentUser.getEmail())
			.username(currentUser.getUsername())
			.status(currentUser.getStatus().name())
			.role(currentUser.getUserRoles().stream().map(role -> role.getRole().name()).toList())
			.registerDate(currentUser.getCreatedAt().toString().substring(0, 10))
			.build();
	}
}
