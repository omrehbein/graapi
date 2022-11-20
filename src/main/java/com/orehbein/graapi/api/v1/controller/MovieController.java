package com.orehbein.graapi.api.v1.controller;

import com.orehbein.graapi.api.v1.dto.movie.MovieDto;
import com.orehbein.graapi.api.v1.dto.movie.MovieInputDto;
import com.orehbein.graapi.domain.service.MovieService;
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

@Api(tags = "Movies")
@RestController
@RequestMapping(path = "/v1/movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    MovieService movieService;

    @ApiOperation("Return all movies")
    @GetMapping
    public List<MovieDto> findAll() {
        return this.movieService.findAll().stream().map(p -> this.modelMapper.map(p, MovieDto.class)).toList();
    }

    @ApiOperation("Return a movie by ID")
    @GetMapping("/{id}")
    public MovieDto findById(@ApiParam(value = "Movie ID", example = "1") @PathVariable Long id) {
        return this.modelMapper.map(this.movieService.findById(id), MovieDto.class);
    }

    @ApiOperation("Add a new movie")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDto add(@ApiParam(name = "body", value = "Movie body") @RequestBody @Valid MovieInputDto movieInputDto) {
        return this.modelMapper.map(this.movieService.create(movieInputDto.getProductionYear(), movieInputDto.getTitle(), movieInputDto.getStudios(), movieInputDto.getProducers(), movieInputDto.isWinner()), MovieDto.class);
    }

    @ApiOperation("Update a movie by ID")
    @PutMapping("/{id}")
    public MovieDto update(@ApiParam(value = "Movie ID", example = "1") @PathVariable Long id, @ApiParam(name = "body", value = "Movie body") @RequestBody @Valid MovieInputDto movieInputDto) {
        return this.modelMapper.map(this.movieService.update(id, movieInputDto.getProductionYear(), movieInputDto.getTitle(), movieInputDto.getStudios(), movieInputDto.getProducers(), movieInputDto.isWinner()), MovieDto.class);
    }

    @ApiOperation("Delete a movie by ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@ApiParam(value = "Movie ID", example = "1") @PathVariable Long id) {
        this.movieService.delete(id);
    }

}
