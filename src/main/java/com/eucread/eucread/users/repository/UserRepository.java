package com.eucread.eucread.users.repository;

import com.eucread.eucread.users.entity.User;
import com.eucread.eucread.users.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findByUsername(String username);
    
    List<User> findByStatus(UserStatus status);
    
    boolean existsByEmail(String email);
    
    boolean existsByUsername(String username);
    
    @Query("SELECT u FROM User u WHERE u.email LIKE %:keyword% OR u.username LIKE %:keyword%")
    List<User> findByEmailOrUsernameContaining(@Param("keyword") String keyword);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userRoles WHERE u.email = :email")
    Optional<User> findByEmailFetchRoles(@Param("email") String email);
}

