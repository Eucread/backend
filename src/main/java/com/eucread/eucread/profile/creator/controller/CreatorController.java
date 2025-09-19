package com.eucread.eucread.profile.creator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eucread.eucread.profile.creator.dto.UpdateProfileReq;
import com.eucread.eucread.profile.creator.service.CreatorService;
import com.eucread.eucread.util.response.Response;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/creator")
public class CreatorController {

	private final CreatorService creatorService;

	@PatchMapping("/profile")
	public ResponseEntity<Response<Void>> updateCreatorProfile(@RequestBody UpdateProfileReq request) {
		creatorService.updateCreatorProfile(request);

		return Response.ok().toResponseEntity();
	}
}
