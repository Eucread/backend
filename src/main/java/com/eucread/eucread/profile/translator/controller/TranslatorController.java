package com.eucread.eucread.profile.translator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eucread.eucread.profile.creator.dto.UpdateProfileReq;
import com.eucread.eucread.profile.translator.service.TranslatorService;
import com.eucread.eucread.util.response.Response;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/translator")
public class TranslatorController {

	private final TranslatorService translatorService;

	@PatchMapping("/profile")
	public ResponseEntity<Response<Void>> updateCreatorProfile(@RequestBody UpdateProfileReq request) {
		translatorService.updateTranslatorProfile(request);

		return Response.ok().toResponseEntity();
	}
}
