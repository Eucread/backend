package com.eucread.eucread.comic.series.repository;

import com.eucread.eucread.comic.series.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    Optional<Series> findById
}
