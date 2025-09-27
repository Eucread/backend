package com.eucread.eucread.comic.page.entity;

import com.eucread.eucread.comic.episode.entity.Episode;

import com.eucread.eucread.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "page")
public class Page<S extends BaseEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "page_id")
    private Long pageId;

    @Column(name = "page_no", nullable = false)
    private Long pageNo;

    @Column(name = "storage_key", nullable = false)
    private String storageKey;

    @Column(name = "width", nullable = false, columnDefinition = "DOUBLE")
    private Double width;

    @Column(name = "height", nullable = false, columnDefinition = "DOUBLE")
    private Double height;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "episode_id")
    private Episode episode;
}
