package com.eucread.eucread.comic.episode.repository;

import com.eucread.eucread.comic.episode.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodeRepository extends JpaRepository< Episode, Long> {

}
