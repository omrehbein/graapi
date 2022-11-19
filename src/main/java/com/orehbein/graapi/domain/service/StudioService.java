package com.orehbein.graapi.domain.service;

import com.orehbein.graapi.domain.entity.StudioEntity;

import java.util.List;

public interface StudioService {
    void createStudio(List<String> studioNames);
}
