package com.orehbein.graapi.api.v1.controller;

import com.orehbein.graapi.api.v1.dto.producer.MinMaxIntervalDto;
import com.orehbein.graapi.api.v1.dto.producer.ProducerDto;
import com.orehbein.graapi.api.v1.dto.producer.ProducerInputDto;
import com.orehbein.graapi.domain.service.ProducerService;
import com.orehbein.graapi.domain.service.ProducerWinnerIntervalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/producers", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProducerController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ProducerService producerService;

    @Autowired
    ProducerWinnerIntervalService producerWinnerIntervalService;

    @GetMapping
    public List<ProducerDto> findAll() {
        return this.producerService.findAll().stream().map(p -> this.modelMapper.map(p, ProducerDto.class)).toList();
    }

    @GetMapping("/{id}")
    public ProducerDto findById(@PathVariable Long id) {
        return this.modelMapper.map(this.producerService.findById(id), ProducerDto.class);
    }

    @GetMapping("/minmaxinterval")
    public MinMaxIntervalDto minMaxInterval() {
        return this.modelMapper.map(this.producerWinnerIntervalService.minMaxInterval(), MinMaxIntervalDto.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProducerDto add(@RequestBody @Valid ProducerInputDto producerInputDto) {
        return this.modelMapper.map(this.producerService.create(producerInputDto.getName()), ProducerDto.class);
    }

    @PutMapping("/{id}")
    public ProducerDto update(@PathVariable Long id, @RequestBody @Valid ProducerInputDto producerInputDto) {
        return this.modelMapper.map(this.producerService.update(id, producerInputDto.getName()), ProducerDto.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.producerService.delete(id);
    }

}
