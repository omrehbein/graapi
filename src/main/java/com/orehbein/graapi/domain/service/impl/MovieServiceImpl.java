package com.orehbein.graapi.domain.service.impl;

import com.orehbein.graapi.domain.entity.MovieEntity;
import com.orehbein.graapi.domain.entity.ProducerEntity;
import com.orehbein.graapi.domain.entity.StudioEntity;
import com.orehbein.graapi.domain.exception.EntityInUseException;
import com.orehbein.graapi.domain.exception.MovieNotFoundException;
import com.orehbein.graapi.domain.exception.ProducerNotFoundException;
import com.orehbein.graapi.domain.exception.StudioNotFoundException;
import com.orehbein.graapi.domain.repository.MovieRepository;
import com.orehbein.graapi.domain.repository.ProducerRepository;
import com.orehbein.graapi.domain.repository.StudioRepository;
import com.orehbein.graapi.domain.service.MovieService;
import com.orehbein.graapi.domain.service.ProducerWinnerIntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MovieServiceImpl implements MovieService {

    public static final String MSG_STUDIO_NOT_FOUND_BY_NAME = "Studio not found by name %s";
    public static final String MSG_PRODUCER_NOT_FOUND_BY_NAME = "Producer not found by name %s";
    private static final String MSG_MOVIE_IN_USE = "Producer with code %d cannot be removed as it is in use";
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
    public MovieEntity create(final Integer year, final String title, final Set<StudioEntity> studioEntitys, final Set<ProducerEntity> producerEntitys, final boolean winner) {

        final MovieEntity movieEntity = new MovieEntity();
        movieEntity.setProductionYear(year);
        movieEntity.setTitle(title);

        movieEntity.setStudios(studioEntitys);
        movieEntity.setProducers(producerEntitys);
        movieEntity.setWinner(winner);

        this.movieRepository.save(movieEntity);

        return movieEntity;
    }

    @Override
    public MovieEntity create(final Integer year, final String title, final List<String> studioNames, final List<String> producerNames, final boolean winner) {

        final List<StudioEntity> studioEntitys = studioNames.stream().map(name -> this.studioRepository.findByName(name).orElseThrow(() -> new StudioNotFoundException(String.format(MSG_STUDIO_NOT_FOUND_BY_NAME, name)))).toList();
        final List<ProducerEntity> producerEntitys = producerNames.stream().map(name -> this.producerRepository.findByName(name).orElseThrow(() -> new ProducerNotFoundException(String.format(MSG_PRODUCER_NOT_FOUND_BY_NAME, name)))).toList();

        final MovieEntity movieEntity = this.create(year, title, new HashSet<>(studioEntitys), new HashSet<>(producerEntitys), winner);

        this.producerWinnerIntervalService.recreateProducerWinnerInterval(movieEntity);

        return movieEntity;
    }

    @Override
    public MovieEntity update(Long id, Integer year, String title, List<String> studioNames, List<String> producerNames, boolean winner) {
        final List<StudioEntity> studioEntitys = studioNames.stream().map(name -> this.studioRepository.findByName(name).orElseThrow(() -> new StudioNotFoundException(String.format(MSG_STUDIO_NOT_FOUND_BY_NAME, name)))).toList();
        final List<ProducerEntity> producerEntitys = producerNames.stream().map(name -> this.producerRepository.findByName(name).orElseThrow(() -> new ProducerNotFoundException(String.format(MSG_PRODUCER_NOT_FOUND_BY_NAME, name)))).toList();

        final MovieEntity movieEntity = this.findById(id);

        movieEntity.setProductionYear(year);
        movieEntity.setTitle(title);
        movieEntity.setStudios(new HashSet<>(studioEntitys));
        movieEntity.setProducers(new HashSet<>(producerEntitys));
        movieEntity.setWinner(winner);

        this.producerWinnerIntervalService.recreateProducerWinnerInterval(movieEntity);

        this.movieRepository.save(movieEntity);

        return movieEntity;
    }

    @Override
    public List<MovieEntity> findAll() {
        return this.movieRepository.findAll();
    }

    @Override
    public MovieEntity findById(Long id) {
        return this.movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
    }

    @Override
    public void delete(Long id) {
        try {
            this.movieRepository.deleteById(id);
            this.movieRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new MovieNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_MOVIE_IN_USE, id));
        }
    }

}
