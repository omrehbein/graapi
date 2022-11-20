package com.orehbein.graapi.domain.service;

import com.orehbein.graapi.domain.entity.MovieEntity;
import com.orehbein.graapi.domain.service.dto.producerwinnerintervalservice.MinMaxIntervalDto;

public interface ProducerWinnerIntervalService {
    void recreateProducerWinnerInterval(MovieEntity movieEntity);

    MinMaxIntervalDto minMaxInterval();
}
