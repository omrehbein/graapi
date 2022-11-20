package com.orehbein.graapi.api.v1.controller;

import com.orehbein.graapi.api.v1.dto.studio.StudioDto;
import com.orehbein.graapi.api.v1.dto.studio.StudioInputDto;
import com.orehbein.graapi.domain.service.StudioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/studio", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudioController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    StudioService studioService;

    @GetMapping
    public List<StudioDto> findAll() {
        return this.studioService.findAll().stream().map(p -> this.modelMapper.map(p, StudioDto.class)).toList();
    }

    @GetMapping("/{id}")
    public StudioDto findById(@PathVariable Long id) {
        return this.modelMapper.map(this.studioService.findById(id), StudioDto.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudioDto add(@RequestBody @Valid StudioInputDto studioInputDto) {
        return this.modelMapper.map(this.studioService.create(studioInputDto.getName()), StudioDto.class);
    }

    @PutMapping("/{id}")
    public StudioDto update(@PathVariable Long id, @RequestBody @Valid StudioInputDto studioInputDto) {
        return this.modelMapper.map(this.studioService.update(id, studioInputDto.getName()), StudioDto.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.studioService.delete(id);
    }

}
