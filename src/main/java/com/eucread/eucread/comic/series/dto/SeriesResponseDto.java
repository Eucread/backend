package com.eucread.eucread.comic.series.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeriesResponseDto {
    private Long id;
    private String title;
    private String thumbnail;
    private Double score;
}
