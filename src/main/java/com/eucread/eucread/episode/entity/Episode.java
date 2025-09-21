package com.eucread.eucread.episode.entity;

import com.eucread.eucread.series.entity.Series;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
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
    private List<Page> pages = new ArrayList<>();
}
