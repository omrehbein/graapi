package com.orehbein.graapi.api.v1.controller;

import com.orehbein.graapi.api.v1.dto.producer.MinMaxIntervalDto;
import com.orehbein.graapi.api.v1.dto.producer.ProducerDto;
import com.orehbein.graapi.api.v1.dto.producer.ProducerInputDto;
import com.orehbein.graapi.domain.service.ProducerService;
import com.orehbein.graapi.domain.service.ProducerWinnerIntervalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Producers")
@RestController
@RequestMapping(path = "/v1/producers", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProducerController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ProducerService producerService;

    @Autowired
    ProducerWinnerIntervalService producerWinnerIntervalService;

    @ApiOperation("Return all producers")
    @GetMapping
    public List<ProducerDto> findAll() {
        return this.producerService.findAll().stream().map(p -> this.modelMapper.map(p, ProducerDto.class)).toList();
    }

    @ApiOperation("Return a producer by ID")
    @GetMapping("/{id}")
    public ProducerDto findById(@ApiParam(value = "Producer ID", example = "1") @PathVariable Long id) {
        return this.modelMapper.map(this.producerService.findById(id), ProducerDto.class);
    }

    @ApiOperation("Get the producers with the longest and shortest gap between two consecutive awards")
    @GetMapping("/minmaxinterval")
    public MinMaxIntervalDto minMaxInterval() {
        return this.modelMapper.map(this.producerWinnerIntervalService.minMaxInterval(), MinMaxIntervalDto.class);
    }

    @ApiOperation("Add a new producer")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProducerDto add(@ApiParam(name = "body", value = "Producer body") @RequestBody @Valid ProducerInputDto producerInputDto) {
        return this.modelMapper.map(this.producerService.create(producerInputDto.getName()), ProducerDto.class);
    }

    @ApiOperation("Update a producer by ID")
    @PutMapping("/{id}")
    public ProducerDto update(@ApiParam(value = "Producer ID", example = "1") @PathVariable Long id, @ApiParam(name = "body", value = "Producer body")  @RequestBody @Valid ProducerInputDto producerInputDto) {
        return this.modelMapper.map(this.producerService.update(id, producerInputDto.getName()), ProducerDto.class);
    }

    @ApiOperation("Delete a producer by ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@ApiParam(value = "Producer ID", example = "1") @PathVariable Long id) {
        this.producerService.delete(id);
    }

}
