package com.eucread.eucread.comic.page.entity;

import com.eucread.eucread.comic.episode.entity.Episode;
import com.eucread.eucread.util.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PageRepository extends JpaRepository<Page<BaseEntity>, Long> {
    void deletePageByEpisode(Episode episode);

    List<Page> finByEpisode(Episode episode);

    Optional<Page> findByEpisodeAndPageNo(Episode episode, Long pageNo);
}
