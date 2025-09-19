package com.eucread.eucread.profile.creator.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eucread.eucread.profile.creator.dto.UpdateProfileReq;
import com.eucread.eucread.profile.creator.entity.CreatorProfile;
import com.eucread.eucread.profile.creator.repository.CreatorRepository;
import com.eucread.eucread.users.entity.User;
import com.eucread.eucread.util.security.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreatorService {

	private final CreatorRepository creatorRepository;
	private final SecurityUtil securityUtil;

	@Transactional
	public void createCreatorProfile(User user) {
		CreatorProfile creatorProfile = CreatorProfile.builder()
			.user(user)
			.penName(user.getUsername())
			.build();

		creatorRepository.save(creatorProfile);
	}

	@Transactional
	public void updateCreatorProfile(UpdateProfileReq request) {
		CreatorProfile creatorProfile = securityUtil.getCurrentUser().getCreatorProfile();

		creatorProfile.setPenName(request.getPenName());
		creatorProfile.setIntroduction(request.getIntroduction());
		creatorRepository.save(creatorProfile);
	}
}
