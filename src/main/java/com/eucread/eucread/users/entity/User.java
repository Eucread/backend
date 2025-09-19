package com.eucread.eucread.users.entity;

import java.util.ArrayList;
import java.util.List;

import com.eucread.eucread.profile.creator.entity.CreatorProfile;
import com.eucread.eucread.profile.translator.entity.TranslatorProfile;
import com.eucread.eucread.users.enums.UserStatus;
import com.eucread.eucread.util.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "email", unique = true)
    private String email;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "username")
    @Setter
    private String username;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> userRoles = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private CreatorProfile creatorProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private TranslatorProfile translatorProfile;

    public void userExit() {
        this.status = UserStatus.DELETED;
    }

}
