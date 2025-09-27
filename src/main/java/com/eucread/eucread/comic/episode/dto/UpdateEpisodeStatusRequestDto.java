package com.eucread.eucread.comic.episode.dto;

import com.eucread.eucread.comic.episode.enums.EpisodeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEpisodeStatusRequestDto {
    private Long episodeId;
    private EpisodeStatus episodeStatus;
}
