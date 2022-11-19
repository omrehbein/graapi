package com.orehbein.graapi.domain.repository;

import com.orehbein.graapi.domain.entity.ProducerEntity;
import com.orehbein.graapi.domain.entity.ProducerWinnerIntervalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProducerWinnerIntervalRepository extends JpaRepository<ProducerWinnerIntervalEntity, Long>, JpaSpecificationExecutor<ProducerWinnerIntervalEntity> {
    List<ProducerWinnerIntervalEntity> findAllByProducer(ProducerEntity producerEntity);
}
