package com.eucread.eucread.comic.episode.controller;

import com.eucread.eucread.comic.episode.dto.EpisodeCreateRequestDto;
import com.eucread.eucread.comic.episode.dto.EpisodeDetailViewResponseDto;
import com.eucread.eucread.comic.episode.dto.EpisodeUpdateRequestDto;
import com.eucread.eucread.comic.episode.dto.UpdateEpisodeStatusRequestDto;
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
//     1. 에피소드 등록
    @PostMapping("/CREATOR/comic/series/{id}/create-episode")
    public ResponseEntity<Response<Void>> createEpisode(@PathVariable Long id, @RequestBody EpisodeCreateRequestDto episodeCreateRequestDto) {
        episodeService.createEpisode(id, episodeCreateRequestDto);
        return Response.ok().toResponseEntity();
    }

//      2. 에피소드 수정
    @PatchMapping("/CREATOR/comic/series/{id}/update-episode")
    public ResponseEntity<Response<Void>> updateEpisode(EpisodeUpdateRequestDto episodeUpdateRequestDto) {
        episodeService.updateEpisode(episodeUpdateRequestDto);
        return Response.ok().toResponseEntity();
    }


    // 3. 에피소드 상세보기
    @GetMapping("comic/series/{id}")
    public ResponseEntity<Response<EpisodeDetailViewResponseDto>> viewEpisode(@RequestParam Long episodeId) {
        EpisodeDetailViewResponseDto episodeDetailViewResponseDto = episodeService.viewEpisode(episodeId);
        return Response.ok(episodeDetailViewResponseDto).toResponseEntity();
    }


    // 4. 에피소드 삭제
    @DeleteMapping("/CREATOR/comic/series/{id}/delete-episode")
    public ResponseEntity<Response<Void>> deleteEpisode(@RequestParam Long episodeId) {
        episodeService.deleteEpisode(episodeId);
        return Response.ok().toResponseEntity();
    }


    // 5. 에피소드 권한 조정
    @PostMapping("/ADMIN/comic/series/{id}/episode-status")
    public ResponseEntity<Response<Void>> updateEpisodeStatus(@RequestBody UpdateEpisodeStatusRequestDto updateEpisodeStatusRequestDto) {
        episodeService.updateEpisodeStatus(updateEpisodeStatusRequestDto);
        return Response.ok().toResponseEntity();
    }


}
