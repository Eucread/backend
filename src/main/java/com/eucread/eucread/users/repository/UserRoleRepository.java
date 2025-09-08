package com.eucread.eucread.users.repository;

import com.eucread.eucread.users.entity.UserRole;
import com.eucread.eucread.users.entity.User;
import com.eucread.eucread.users.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    
    List<UserRole> findByUser(User user);
    
    List<UserRole> findByUserUserId(Long userId);
    
    Optional<UserRole> findByUserAndRole(User user, Role role);
    
    boolean existsByUserAndRole(User user, Role role);
    
    void deleteByUserAndRole(User user, Role role);
}

