package com.eucread.eucread.comic.episode.controller;

import com.eucread.eucread.comic.episode.dto.EpisodeCreateRequestDto;
import com.eucread.eucread.comic.episode.entity.Episode;
import com.eucread.eucread.comic.episode.service.EpisodeService;
import com.eucread.eucread.util.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EpisodeController {
    private final EpisodeService episodeService;
    // 1. 에피소드 등록
//    @PostMapping("/CREATOR/comic/series/{id}/create")
//    public ResponseEntity<?> createEpisode(@PathVariable Long id, @RequestBody EpisodeCreateRequestDto episodeCreateRequestDto) {
//        episodeService.createEpisode(id, episodeCreateRequestDto);
//        return Response.ok().toResponseEntity();
//    }

    // 2. 에피소드 수정
    // 3. 에피소드 상세보기
    // 4. 에피소드 삭제

    // 5. 에피소드 권한 조정
}
