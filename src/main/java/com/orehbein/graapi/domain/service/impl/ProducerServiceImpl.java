package com.orehbein.graapi.domain.service.impl;

import com.orehbein.graapi.domain.entity.ProducerEntity;
import com.orehbein.graapi.domain.exception.EntityInUseException;
import com.orehbein.graapi.domain.exception.ProducerNotFoundException;
import com.orehbein.graapi.domain.repository.ProducerRepository;
import com.orehbein.graapi.domain.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducerServiceImpl implements ProducerService {

    private static final String MSG_PRODUCER_IN_USE = "Producer with code %d cannot be removed as it is in use";

    private final ProducerRepository producerRepository;


    @Autowired
    public ProducerServiceImpl(ProducerRepository producerRepository){
        this.producerRepository = producerRepository;
    }

    public ProducerEntity getNewEntityByName(final String name) {
        final ProducerEntity entity = new ProducerEntity();
        entity.setName(name);
        return entity;

    }

    @Override
    public void create(final List<String> producerNames) {
        final List<ProducerEntity> producerEntities = producerNames.stream().map(name -> this.getNewEntityByName(name)).toList();
        this.producerRepository.saveAll(producerEntities);
    }

    @Override
    public ProducerEntity create(final String name) {
        final ProducerEntity producerEntity = this.getNewEntityByName(name);
        return this.producerRepository.save(producerEntity);
    }

    @Override
    public List<ProducerEntity> findAll() {
        return this.producerRepository.findAll();
    }

    @Override
    public ProducerEntity findById(final Long id) {
        return this.producerRepository.findById(id).orElseThrow(() -> new ProducerNotFoundException(id));
    }

    @Override
    public ProducerEntity update(Long id, String name) {
        final ProducerEntity producerEntity = this.findById(id);
        producerEntity.setName(name);
        return this.producerRepository.save(producerEntity);
    }

    @Override
    public void delete(Long id) {
        try {
            this.producerRepository.deleteById(id);
            this.producerRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new ProducerNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_PRODUCER_IN_USE, id));
        }
    }

}