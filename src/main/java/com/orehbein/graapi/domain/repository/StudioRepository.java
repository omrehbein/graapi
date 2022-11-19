package com.orehbein.graapi.domain.repository;

import com.orehbein.graapi.domain.entity.StudioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface StudioRepository extends JpaRepository<StudioEntity, Long>, JpaSpecificationExecutor<StudioEntity> {

    Optional<StudioEntity> findByName(String studioName);
}
