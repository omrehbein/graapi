package com.orehbein.graapi.domain.repository;

import com.orehbein.graapi.domain.entity.ProducerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ProducerRepository extends JpaRepository<ProducerEntity, Long>, JpaSpecificationExecutor<ProducerEntity> {

    Optional<ProducerEntity> findByName(String producerName);
}
