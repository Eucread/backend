package com.eucread.eucread.users.service;

import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eucread.eucread.admin.component.AdminWhitelist;
import com.eucread.eucread.exception.BusinessException;
import com.eucread.eucread.profile.creator.service.CreatorService;
import com.eucread.eucread.profile.translator.service.TranslatorService;
import com.eucread.eucread.users.auth.exception.AuthErrorCode;
import com.eucread.eucread.users.dto.request.EditUserInfoReq;
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

	private final CreatorService creatorService;
	private final TranslatorService translatorService;
	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final PasswordEncoder passwordEncoder;
	private final AdminWhitelist adminWhitelist;
	private final SecurityUtil securityUtil;
	private final StringRedisTemplate redisTemplate;

	@Transactional
	public void register(RegisterReq registerReq) {

		String key = "auth:email:" + registerReq.getEmail();
		String isAuthed = redisTemplate.opsForValue().get(key);

		if (!"true".equals(isAuthed)) {
			throw new BusinessException(UserErrorCode.DIFFERENT_EMAIL);
		}

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

		redisTemplate.delete(key);

		if (roles.contains(Role.CREATOR)) {
			creatorService.createCreatorProfile(savedUser);
		}

		if (roles.contains(Role.TRANSLATOR)) {
			translatorService.createTranslatorProfile(savedUser);
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

	@Transactional
	public void editMyInfo(EditUserInfoReq editUserInfoReq) {
		User currentUser = securityUtil.getCurrentUser();

		String username = editUserInfoReq.getUsername();

		checkUserName(username);

		currentUser.setUsername(username);
	}

	@Transactional
	public void userExit() {
		User currentUser = securityUtil.getCurrentUser();

		currentUser.userExit();
	}

}
