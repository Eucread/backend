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
public class EpisodeDetailViewResponseDto {
    private Long id;
    private Long number;
    private String title;
    List<Pages> pages;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pages {
        private String storageKey;
        private Long number;
        private Double width;
        private Double height;
    }
}
