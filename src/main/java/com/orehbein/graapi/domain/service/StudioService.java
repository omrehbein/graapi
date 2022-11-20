package com.orehbein.graapi.domain.service;

import com.orehbein.graapi.domain.entity.StudioEntity;

import java.util.Collection;
import java.util.List;

public interface StudioService {
    void create(List<String> studioNames);

    StudioEntity create(String studioName);

    List<StudioEntity> findAll();

    StudioEntity findById(Long id);

    StudioEntity update(Long id, String name);

    void delete(Long id);
}
