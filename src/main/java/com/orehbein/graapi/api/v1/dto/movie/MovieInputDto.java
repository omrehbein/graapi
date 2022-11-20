package com.orehbein.graapi.api.v1.dto.movie;

import com.orehbein.graapi.api.v1.dto.producer.ProducerDto;
import com.orehbein.graapi.api.v1.dto.studio.StudioDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class MovieInputDto {

    @NotNull
    private Integer productionYear;

    @NotNull
    private String title;

    @NotNull
    private boolean winner;

    @NotNull
    private List<String> studios;

    @NotNull
    private List<String> producers;

}