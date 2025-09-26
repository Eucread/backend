package com.eucread.eucread.comic.series.dto;

import com.eucread.eucread.comic.series.enums.SeriesStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeriesStatusUpdateRequestDto {
    private Long seriesId;
    private SeriesStatus status;
}
