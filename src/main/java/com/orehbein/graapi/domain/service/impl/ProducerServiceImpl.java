package com.orehbein.graapi.domain.service.impl;

import com.orehbein.graapi.domain.entity.ProducerEntity;
import com.orehbein.graapi.domain.repository.MovieRepository;
import com.orehbein.graapi.domain.repository.ProducerRepository;
import com.orehbein.graapi.domain.repository.ProducerWinnerIntervalRepository;
import com.orehbein.graapi.domain.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducerServiceImpl implements ProducerService {

    private final ProducerRepository producerRepository;

    private final MovieRepository movieRepository;

    private final ProducerWinnerIntervalRepository producerWinnerIntervalRepository;


    @Autowired
    public ProducerServiceImpl(ProducerRepository producerRepository, ProducerWinnerIntervalRepository producerWinnerIntervalRepository, MovieRepository movieRepository){
        this.producerRepository = producerRepository;
        this.producerWinnerIntervalRepository = producerWinnerIntervalRepository;
        this.movieRepository = movieRepository;
    }

    public ProducerEntity getNewEntityByName(String name) {
        final ProducerEntity entity = new ProducerEntity();
        entity.setName(name);
        return entity;

    }

    @Override
    public void createProducer(List<String> producerNames) {
        final List<ProducerEntity> producerEntities = producerNames.stream().map(name -> this.getNewEntityByName(name)).toList();
        this.producerRepository.saveAll(producerEntities);
    }

}
