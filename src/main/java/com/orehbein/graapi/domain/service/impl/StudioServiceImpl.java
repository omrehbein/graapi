package com.orehbein.graapi.domain.service.impl;

import com.orehbein.graapi.domain.entity.StudioEntity;
import com.orehbein.graapi.domain.exception.EntityInUseException;
import com.orehbein.graapi.domain.exception.StudioNotFoundException;
import com.orehbein.graapi.domain.repository.StudioRepository;
import com.orehbein.graapi.domain.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudioServiceImpl implements StudioService {

    private static final String MSG_STUDIO_IN_USE = "Studio with code %d cannot be removed as it is in use";
    private final StudioRepository studioRepository;

    @Autowired
    public StudioServiceImpl(StudioRepository studioRepository){
        this.studioRepository = studioRepository;
    }

    private StudioEntity getNewEntityByName(final String name) {
        final StudioEntity studioEntity = new StudioEntity();
        studioEntity.setName(name);
        return studioEntity;
    }

    @Override
    public void create(List<String> studioNames) {
        final List<StudioEntity> studioEntities = studioNames.stream().map(name -> this.getNewEntityByName(name)).toList();
        this.studioRepository.saveAll(studioEntities);
    }

    @Override
    public StudioEntity create(String name) {
        final StudioEntity studioEntity = this.getNewEntityByName(name);
        return this.studioRepository.save(studioEntity);
    }

    @Override
    public List<StudioEntity> findAll() {
        return this.studioRepository.findAll();
    }

    @Override
    public StudioEntity findById(Long id) {
        return this.studioRepository.findById(id).orElseThrow(() -> new StudioNotFoundException(id));
    }

    @Override
    public StudioEntity update(Long id, String name) {
        final StudioEntity studioEntity = this.findById(id);
        studioEntity.setName(name);
        return this.studioRepository.save(studioEntity);
    }

    @Override
    public void delete(Long id) {
        try {
            this.studioRepository.deleteById(id);
            this.studioRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new StudioNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_STUDIO_IN_USE, id));
        }
    }
}
