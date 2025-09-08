package com.eucread.eucread.users.entity;

import java.util.ArrayList;
import java.util.List;

import com.eucread.eucread.users.enums.UserStatus;
import com.eucread.eucread.util.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

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
    private String username;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;

    @Column(name = "email_auth")
    private boolean emailAuth = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> userRoles = new ArrayList<>();



}
