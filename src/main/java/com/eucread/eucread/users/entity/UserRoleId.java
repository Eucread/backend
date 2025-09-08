package com.eucread.eucread.users.entity;

import java.io.Serializable;

import com.eucread.eucread.users.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserRoleId implements Serializable {
    
    private Long user;
    private Role role;
}
