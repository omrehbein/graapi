package com.orehbein.graapi.api.v1.controller;

import com.orehbein.graapi.api.v1.dto.studio.StudioDto;
import com.orehbein.graapi.api.v1.dto.studio.StudioInputDto;
import com.orehbein.graapi.domain.service.StudioService;
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

@Api(tags = "Studios")
@RestController
@RequestMapping(path = "/v1/studios", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudioController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    StudioService studioService;

    @ApiOperation("Return all studios")
    @GetMapping
    public List<StudioDto> findAll() {
        return this.studioService.findAll().stream().map(p -> this.modelMapper.map(p, StudioDto.class)).toList();
    }

    @ApiOperation("Return a studios by ID")
    @GetMapping("/{id}")
    public StudioDto findById(@ApiParam(value = "Studio ID", example = "1") @PathVariable Long id) {
        return this.modelMapper.map(this.studioService.findById(id), StudioDto.class);
    }

    @ApiOperation("Add a new studio")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudioDto add(@ApiParam(name = "body", value = "Studio body") @RequestBody @Valid StudioInputDto studioInputDto) {
        return this.modelMapper.map(this.studioService.create(studioInputDto.getName()), StudioDto.class);
    }

    @ApiOperation("Update a studio by ID")
    @PutMapping("/{id}")
    public StudioDto update(@ApiParam(value = "Studio ID", example = "1") @PathVariable Long id, @ApiParam(name = "body", value = "Studio body") @RequestBody @Valid StudioInputDto studioInputDto) {
        return this.modelMapper.map(this.studioService.update(id, studioInputDto.getName()), StudioDto.class);
    }

    @ApiOperation("Delete a studio by ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@ApiParam(value = "Studio ID", example = "1") @PathVariable Long id) {
        this.studioService.delete(id);
    }

}
