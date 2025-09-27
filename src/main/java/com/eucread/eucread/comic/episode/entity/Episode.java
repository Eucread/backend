package com.eucread.eucread.comic.episode.entity;

import com.eucread.eucread.comic.episode.enums.EpisodeStatus;
import com.eucread.eucread.comic.page.entity.Page;
import com.eucread.eucread.comic.series.entity.Series;
import com.eucread.eucread.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "episode")
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "episode_id")
    private Long episodeId;

    // 시리즈 아이디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id", nullable = false)
    private Series series;

    @Column(nullable = false)
    private Long number; // 회차 정보

    @Column(nullable = false)
    private String title; // 제목

    @Column(name = "scheduled_at", nullable = false)
    private LocalDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    private EpisodeStatus status;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    private Long views;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String thumbnail;

    @Column(name = "average_star", columnDefinition = "DOUBLE")
    private Double averageStar;

    @OneToMany(mappedBy = "episode", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Page<BaseEntity>> pages = new ArrayList<>();


}
