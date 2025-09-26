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
public class SeriesCreateRequestDto  {
    private String title;
    private String genre;
    private String thumbnail;
    private String introduction;


}
