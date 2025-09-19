package com.eucread.eucread.profile.translator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eucread.eucread.profile.translator.entity.TranslatorProfile;

public interface TranslatorRepository extends JpaRepository<TranslatorProfile, Long> {
}
