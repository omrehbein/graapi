package com.orehbein.graapi.domain.service;

import com.orehbein.graapi.domain.entity.MovieEntity;
import com.orehbein.graapi.domain.entity.ProducerEntity;
import com.orehbein.graapi.domain.entity.StudioEntity;

import java.util.List;
import java.util.Set;

public interface MovieService {
    MovieEntity create(Integer year, String title, StudioEntity studioEntity, Set<ProducerEntity> producerEntitys, boolean winner);

    MovieEntity create(Integer year, String title, String studioName, List<String> producerNames, boolean winner);

}
