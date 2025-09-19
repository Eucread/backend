package com.eucread.eucread.profile.translator.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eucread.eucread.profile.creator.dto.UpdateProfileReq;
import com.eucread.eucread.profile.creator.entity.CreatorProfile;
import com.eucread.eucread.profile.translator.entity.TranslatorProfile;
import com.eucread.eucread.profile.translator.repository.TranslatorRepository;
import com.eucread.eucread.users.entity.User;
import com.eucread.eucread.util.security.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TranslatorService {

	private final TranslatorRepository translatorRepository;
	private final SecurityUtil securityUtil;

	@Transactional
	public void createTranslatorProfile(User user) {
		TranslatorProfile translatorProfile = TranslatorProfile.builder()
			.user(user)
			.penName(user.getUsername())
			.build();

		translatorRepository.save(translatorProfile);
	}

	@Transactional
	public void updateTranslatorProfile(UpdateProfileReq request) {
		TranslatorProfile translatorProfile = securityUtil.getCurrentUser().getTranslatorProfile();

		translatorProfile.setPenName(request.getPenName());
		translatorProfile.setIntroduction(request.getIntroduction());
		translatorRepository.save(translatorProfile);
	}
}
