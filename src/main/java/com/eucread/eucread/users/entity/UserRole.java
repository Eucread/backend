package com.eucread.eucread.users.entity;

import com.eucread.eucread.users.enums.Role;
import com.eucread.eucread.users.entity.UserRoleId;
import com.eucread.eucread.users.entity.User;
import com.eucread.eucread.util.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "user_role")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(UserRoleId.class)
public class UserRole extends BaseEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
}
