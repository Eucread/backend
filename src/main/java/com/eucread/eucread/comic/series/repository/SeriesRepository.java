package com.eucread.eucread.comic.series.repository;


import com.eucread.eucread.comic.series.entity.Series;
import com.eucread.eucread.comic.series.enums.SeriesStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    Optional<Series> findById(Long id);
    Page<Series> findByStatus(SeriesStatus seriesStatus, Pageable pageable);
}
