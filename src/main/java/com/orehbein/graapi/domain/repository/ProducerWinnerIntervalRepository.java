package com.orehbein.graapi.domain.repository;

import com.orehbein.graapi.domain.entity.ProducerEntity;
import com.orehbein.graapi.domain.entity.ProducerWinnerIntervalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ProducerWinnerIntervalRepository extends JpaRepository<ProducerWinnerIntervalEntity, Long>, JpaSpecificationExecutor<ProducerWinnerIntervalEntity> {
    List<ProducerWinnerIntervalEntity> findAllByProducer(ProducerEntity producerEntity);

    Optional<ProducerWinnerIntervalEntity> findFirstByOrderByIntervalYears();

    Optional<ProducerWinnerIntervalEntity> findFirstByOrderByIntervalYearsDesc();

    List<ProducerWinnerIntervalEntity> findAllByIntervalYears(Integer iIntervalYears);

    Optional<Integer> getFirstIntervalYearsByOrderByIntervalYears();

    Optional<Integer> findFirstIntervalYearsByOrderByIntervalYearsDesc();
}
