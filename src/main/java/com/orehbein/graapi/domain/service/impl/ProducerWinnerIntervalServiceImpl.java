package com.orehbein.graapi.domain.service.impl;

import com.orehbein.graapi.domain.entity.MovieEntity;
import com.orehbein.graapi.domain.entity.ProducerEntity;
import com.orehbein.graapi.domain.entity.ProducerWinnerIntervalEntity;
import com.orehbein.graapi.domain.repository.MovieRepository;
import com.orehbein.graapi.domain.repository.ProducerWinnerIntervalRepository;
import com.orehbein.graapi.domain.service.ProducerWinnerIntervalService;
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
        if (movieEntity.getWinner()) {
            return;
        }
        for (ProducerEntity producerEntity : movieEntity.getProducers()) {
            final List<MovieEntity> movieEntitys = this.movieRepository.findAllByWinnerAndProducersOrderByProductionYear(true,producerEntity);
            final List<ProducerWinnerIntervalEntity> producerWinnerIntervalEntitys = this.producerWinnerIntervalRepository.findAllByProducer(producerEntity);
            this.producerWinnerIntervalRepository.deleteAll(producerWinnerIntervalEntitys);

            MovieEntity previousMovieEntity = null;
            for (MovieEntity followingMovieEntity : movieEntitys){
                if (previousMovieEntity != null){
                    final ProducerWinnerIntervalEntity producerWinnerIntervalEntity = new ProducerWinnerIntervalEntity();
                    producerWinnerIntervalEntity.setProducer(producerEntity);
                    producerWinnerIntervalEntity.setPreviousMovie(previousMovieEntity);
                    producerWinnerIntervalEntity.setFollowingMovie(followingMovieEntity);
                    producerWinnerIntervalEntity.setIntervalYears(followingMovieEntity.getProductionYear() - previousMovieEntity.getProductionYear());
                    this.producerWinnerIntervalRepository.save(producerWinnerIntervalEntity);
                }

                previousMovieEntity = followingMovieEntity;
            }

        }
    }

}
