package com.eucread.eucread.profile.creator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eucread.eucread.profile.creator.entity.CreatorProfile;

public interface CreatorRepository extends JpaRepository<CreatorProfile, Long> {
}
