package com.orehbein.graapi.domain.service.impl;

import com.orehbein.graapi.domain.entity.StudioEntity;
import com.orehbein.graapi.domain.repository.StudioRepository;
import com.orehbein.graapi.domain.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudioServiceImpl implements StudioService {

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
    public void createStudio(List<String> studioNames) {
        final List<StudioEntity> studioEntities = studioNames.stream().map(name -> this.getNewEntityByName(name)).toList();
        this.studioRepository.saveAll(studioEntities);
    }
}
