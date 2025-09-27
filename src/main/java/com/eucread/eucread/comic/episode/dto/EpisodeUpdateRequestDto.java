package com.eucread.eucread.comic.episode.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeUpdateRequestDto {
    private Long id;
    private String title;
    private List<EpisodeCreateRequestDto.PageDto> pages;
    private String thumbnail;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageDto {
        private Long pageNo;
        private String storageKey;
        private Double height;
        private Double width;
    }
}
