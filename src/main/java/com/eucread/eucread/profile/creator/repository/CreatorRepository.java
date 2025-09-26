package com.eucread.eucread.profile.creator.repository;

import com.eucread.eucread.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eucread.eucread.profile.creator.entity.CreatorProfile;

import java.util.Optional;

public interface CreatorRepository extends JpaRepository<CreatorProfile, Long> {
    Optional<CreatorProfile> findCreatorProfileByUser(User user);
}
