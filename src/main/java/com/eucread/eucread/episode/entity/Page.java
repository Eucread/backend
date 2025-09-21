package com.eucread.eucread.episode.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "page")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "page_id")
    private Long pageId;

    @Column(name = "episode_id", nullable = false)
    private Long episodeId;

    @Column(name = "page_no", nullable = false)
    private Long pageNo;

    @Column(name = "storage_key", nullable = false)
    private String storageKey;

    @Column(name = "width", nullable = false)
    private Integer width;

    @Column(name = "height", nullable = false)
    private Integer height;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "episode_id")
    private Episode episode;
}
