package com.orehbein.graapi.api.v1.controller;

import com.orehbein.graapi.api.v1.dto.movie.MovieDto;
import com.orehbein.graapi.api.v1.dto.movie.MovieInputDto;
import com.orehbein.graapi.api.v1.dto.studio.StudioDto;
import com.orehbein.graapi.api.v1.dto.studio.StudioInputDto;
import com.orehbein.graapi.domain.service.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/movie", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    MovieService movieService;

    @GetMapping
    public List<MovieDto> findAll() {
        return this.movieService.findAll().stream().map(p -> this.modelMapper.map(p, MovieDto.class)).toList();
    }

    @GetMapping("/{id}")
    public MovieDto findById(@PathVariable Long id) {
        return this.modelMapper.map(this.movieService.findById(id), MovieDto.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDto add(@RequestBody @Valid MovieInputDto movieInputDto) {
        return this.modelMapper.map(this.movieService.create(movieInputDto.getProductionYear(), movieInputDto.getTitle(), movieInputDto.getStudios(), movieInputDto.getProducers(), movieInputDto.isWinner()), MovieDto.class);
    }

    @PutMapping("/{id}")
    public MovieDto update(@PathVariable Long id, @RequestBody @Valid MovieInputDto movieInputDto) {
        return this.modelMapper.map(this.movieService.update(id, movieInputDto.getProductionYear(), movieInputDto.getTitle(), movieInputDto.getStudios(), movieInputDto.getProducers(), movieInputDto.isWinner()), MovieDto.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.movieService.delete(id);
    }

}
