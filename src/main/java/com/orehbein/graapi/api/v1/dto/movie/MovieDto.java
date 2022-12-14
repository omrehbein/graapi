package com.orehbein.graapi.api.v1.dto.movie;

import com.orehbein.graapi.api.v1.dto.producer.ProducerDto;
import com.orehbein.graapi.api.v1.dto.studio.StudioDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class MovieDto {

    @NotNull
    private Long id;

    @NotNull
    private Integer productionYear;

    @NotNull
    private String title;

    @NotNull
    private Boolean winner;

    @NotNull
    private Set<StudioDto> studios;

    @NotNull
    private Set<ProducerDto> producers;

}