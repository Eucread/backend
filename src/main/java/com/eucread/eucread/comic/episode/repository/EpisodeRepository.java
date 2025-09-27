package com.eucread.eucread.comic.episode.repository;

import com.eucread.eucread.comic.episode.entity.Episode;
import com.eucread.eucread.comic.series.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpisodeRepository extends JpaRepository< Episode, Long> {
    long countBySeries(Series series);

    List<Episode> findEpisodeBySeries(Series series);
}
