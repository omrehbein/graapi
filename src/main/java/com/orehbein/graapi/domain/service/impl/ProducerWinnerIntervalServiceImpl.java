package com.orehbein.graapi.domain.service.impl;

import com.orehbein.graapi.domain.entity.MovieEntity;
import com.orehbein.graapi.domain.entity.ProducerEntity;
import com.orehbein.graapi.domain.entity.ProducerWinnerIntervalEntity;
import com.orehbein.graapi.domain.repository.MovieRepository;
import com.orehbein.graapi.domain.repository.ProducerWinnerIntervalRepository;
import com.orehbein.graapi.domain.service.ProducerWinnerIntervalService;
import com.orehbein.graapi.domain.service.dto.producerwinnerintervalservice.MinMaxIntervalDto;
import com.orehbein.graapi.domain.service.dto.producerwinnerintervalservice.MinMaxIntervalRecordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducerWinnerIntervalServiceImpl implements ProducerWinnerIntervalService {

    private final ProducerWinnerIntervalRepository producerWinnerIntervalRepository;

    private final MovieRepository movieRepository;

    @Autowired
    public ProducerWinnerIntervalServiceImpl(ProducerWinnerIntervalRepository producerWinnerIntervalRepository, MovieRepository movieRepository){
        this.producerWinnerIntervalRepository = producerWinnerIntervalRepository;
        this.movieRepository = movieRepository;

    }

    @Override
    public void recreateProducerWinnerInterval(MovieEntity movieEntity) {
        if (!movieEntity.getWinner()) {
            return;
        }
        for (ProducerEntity producerEntity : movieEntity.getProducers()) {
            final List<MovieEntity> movieEntitys = this.movieRepository.findAllByWinnerAndProducersOrderByProductionYear(true,producerEntity);
            this.deleteOldProducerWinnerIntervals(producerEntity);
            MovieEntity previousMovieEntity = null;
            for (MovieEntity followingMovieEntity : movieEntitys){
                this.createProducerWinnerInterval(producerEntity, previousMovieEntity, followingMovieEntity);
                previousMovieEntity = followingMovieEntity;
            }

        }
    }

    private void deleteOldProducerWinnerIntervals(ProducerEntity producerEntity) {
        final List<ProducerWinnerIntervalEntity> producerWinnerIntervalEntitys = this.producerWinnerIntervalRepository.findAllByProducer(producerEntity);
        this.producerWinnerIntervalRepository.deleteAll(producerWinnerIntervalEntitys);
    }

    private void createProducerWinnerInterval(ProducerEntity producerEntity, MovieEntity previousMovieEntity, MovieEntity followingMovieEntity) {
        if (previousMovieEntity == null){
            return;
        }

        this.producerWinnerIntervalRepository.save(this.getProducerWinnerIntervalEntity(producerEntity, previousMovieEntity, followingMovieEntity));
    }

    private static ProducerWinnerIntervalEntity getProducerWinnerIntervalEntity(ProducerEntity producerEntity, MovieEntity previousMovieEntity, MovieEntity followingMovieEntity) {
        final ProducerWinnerIntervalEntity producerWinnerIntervalEntity = new ProducerWinnerIntervalEntity();
        producerWinnerIntervalEntity.setProducer(producerEntity);
        producerWinnerIntervalEntity.setPreviousMovie(previousMovieEntity);
        producerWinnerIntervalEntity.setFollowingMovie(followingMovieEntity);
        producerWinnerIntervalEntity.setIntervalYears(followingMovieEntity.getProductionYear() - previousMovieEntity.getProductionYear());
        return producerWinnerIntervalEntity;
    }

    @Override
    //@Transactional
    public MinMaxIntervalDto minMaxInterval() {
        final MinMaxIntervalDto minMaxIntervalDto = new MinMaxIntervalDto();

        final var minIntervalYears = this.producerWinnerIntervalRepository.findFirstByOrderByIntervalYears().orElseGet(() -> new ProducerWinnerIntervalEntity());
        final var maxIntervalYears = this.producerWinnerIntervalRepository.findFirstByOrderByIntervalYearsDesc().orElseGet(() -> new ProducerWinnerIntervalEntity());

        final List<ProducerWinnerIntervalEntity> minProducerWinnerIntervalEntitys = this.producerWinnerIntervalRepository.findAllByIntervalYears(minIntervalYears.getIntervalYears());
        final List<ProducerWinnerIntervalEntity> maxProducerWinnerIntervalEntitys = this.producerWinnerIntervalRepository.findAllByIntervalYears(maxIntervalYears.getIntervalYears());

        minMaxIntervalDto.setMin( minProducerWinnerIntervalEntitys.stream().map(producerWinnerIntervalEntity -> this.getMinMaxIntervalRecordDto(producerWinnerIntervalEntity)).toList() );
        minMaxIntervalDto.setMax( maxProducerWinnerIntervalEntitys.stream().map(producerWinnerIntervalEntity -> this.getMinMaxIntervalRecordDto(producerWinnerIntervalEntity)).toList() );


        return minMaxIntervalDto;
    }

    private static MinMaxIntervalRecordDto getMinMaxIntervalRecordDto(ProducerWinnerIntervalEntity producerWinnerIntervalEntity) {
        final MinMaxIntervalRecordDto minMaxIntervalRecordDto = new MinMaxIntervalRecordDto();
        minMaxIntervalRecordDto.setInterval(producerWinnerIntervalEntity.getIntervalYears());
        minMaxIntervalRecordDto.setProducer(producerWinnerIntervalEntity.getProducer().getName());
        minMaxIntervalRecordDto.setPreviousWin(producerWinnerIntervalEntity.getPreviousMovie().getProductionYear());
        minMaxIntervalRecordDto.setFollowingWin(producerWinnerIntervalEntity.getFollowingMovie().getProductionYear());

        return minMaxIntervalRecordDto;
    }

}
