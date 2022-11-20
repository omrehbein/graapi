package com.orehbein.graapi.domain.service.impl;

import com.orehbein.graapi.domain.entity.MovieEntity;
import com.orehbein.graapi.domain.entity.ProducerEntity;
import com.orehbein.graapi.domain.entity.StudioEntity;
import com.orehbein.graapi.domain.repository.MovieRepository;
import com.orehbein.graapi.domain.repository.ProducerRepository;
import com.orehbein.graapi.domain.repository.StudioRepository;
import com.orehbein.graapi.domain.service.MovieService;
import com.orehbein.graapi.domain.service.ProducerWinnerIntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MovieServiceImpl implements MovieService {

    private final ProducerRepository producerRepository;

    private final StudioRepository studioRepository;

    private final MovieRepository movieRepository;

    private final ProducerWinnerIntervalService producerWinnerIntervalService;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, ProducerRepository producerRepository, StudioRepository studioRepository, ProducerWinnerIntervalService producerWinnerIntervalService){
        this.movieRepository = movieRepository;
        this.producerRepository = producerRepository;
        this.studioRepository = studioRepository;
        this.producerWinnerIntervalService = producerWinnerIntervalService;
    }

    @Override
    public MovieEntity create(Integer year, String title, StudioEntity studioEntity, Set<ProducerEntity> producerEntitys, boolean winner) {

        final MovieEntity movieEntity = new MovieEntity();
        movieEntity.setProductionYear(year);
        movieEntity.setTitle(title);

        movieEntity.setStudio(studioEntity);
        movieEntity.setProducers(producerEntitys);
        movieEntity.setWinner(winner);

        this.movieRepository.save(movieEntity);

        return movieEntity;
    }

    @Override
    public MovieEntity create(final Integer year, final String title, final String studioName, final List<String> producerNames, final boolean winner) {
        final StudioEntity studioEntity = this.studioRepository.findByName(studioName).orElseThrow(() -> new RuntimeException("Studio not fond"));
        final List<ProducerEntity> producerEntitys = producerNames.stream().map(name -> this.producerRepository.findByName(name).orElseThrow(() -> new RuntimeException("Producer not fond"))).toList();
        final MovieEntity movieEntity = this.create(year, title, studioEntity, new HashSet<>(producerEntitys), winner);

        this.producerWinnerIntervalService.recreateProducerWinnerInterval(movieEntity);

        return movieEntity;
    }

}
