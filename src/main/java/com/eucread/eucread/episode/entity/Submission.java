package com.eucread.eucread.episode.entity;

import jakarta.persistence.*;
import lombok.*;


// Submission 엔티티
@Entity
@Table(name = "submission")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "episode_id")
    private Long episodeId;

    @Column(name = "censor_pass", nullable = false)
    private Boolean censorPass;

    // Episode와의 관계 설정 (One-to-One)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "episode_id", insertable = false, updatable = false)
    private Episode episode;
}