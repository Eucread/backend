package com.eucread.eucread.comic.series.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeriesDetailViewResponseDto {
    private Long seriesId;
    private String title;
    private String thumbnail;
    private Double score;
    private String author;
    private String genre;
    private List<EpisodeDto> episodeDtoList;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EpisodeDto {
        private Long episodeId;
        private Long number;
        private String title;
        private Double averageStar;
    }
}
