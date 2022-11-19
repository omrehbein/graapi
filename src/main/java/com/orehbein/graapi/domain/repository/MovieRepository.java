package com.orehbein.graapi.domain.repository;

import com.orehbein.graapi.domain.entity.MovieEntity;
import com.orehbein.graapi.domain.entity.ProducerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieEntity, Long>, JpaSpecificationExecutor<MovieEntity> {
    List<MovieEntity> findAllByWinnerAndProducersOrderByProductionYear(boolean winner, ProducerEntity producerEntity);
}
