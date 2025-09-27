package com.eucread.eucread.comic.series.repository;

import com.eucread.eucread.comic.series.entity.Series;
import com.eucread.eucread.comic.series.entity.SeriesCreator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeriesCreatorRepository extends JpaRepository<SeriesCreator, Long> {
    Optional<SeriesCreator> findSeriesCreatorBySeries(Series series);
}
