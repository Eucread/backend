package com.eucread.eucread.episode.entity;

import com.eucread.eucread.users.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "readinghistory")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "key")
    private Long key;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "page_id", nullable = false)
    private Long pageId;

    // User와의 관계 설정 (Many-to-One)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    // Page와의 관계 설정 (Many-to-One)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id", insertable = false, updatable = false)
    private Page page;
}