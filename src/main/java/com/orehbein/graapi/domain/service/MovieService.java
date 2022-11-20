package com.orehbein.graapi.domain.service;

import com.orehbein.graapi.domain.entity.MovieEntity;
import com.orehbein.graapi.domain.entity.ProducerEntity;
import com.orehbein.graapi.domain.entity.StudioEntity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface MovieService {
    MovieEntity create(Integer year, String title, Set<StudioEntity> studioEntitys, Set<ProducerEntity> producerEntitys, boolean winner);

    MovieEntity create(Integer year, String title, List<String> studioNames, List<String> producerNames, boolean winner);

    Object update(Long id, Integer year, String title, List<String> studioNames, List<String> producerNames, boolean winner);

    List<MovieEntity> findAll();

    MovieEntity findById(Long id);


    void delete(Long id);
}
