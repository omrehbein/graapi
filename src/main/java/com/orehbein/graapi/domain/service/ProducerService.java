package com.orehbein.graapi.domain.service;

import com.orehbein.graapi.domain.entity.ProducerEntity;

import java.util.List;

public interface ProducerService {
    void create(List<String> producerNames);

    ProducerEntity create(String producerName);

    List<ProducerEntity> findAll();

    ProducerEntity findById(Long id);

    ProducerEntity update(Long id, String name);

    void delete(Long id);

}
