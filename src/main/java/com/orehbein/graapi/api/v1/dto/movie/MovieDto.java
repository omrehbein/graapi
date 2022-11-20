package com.orehbein.graapi.api.v1.dto.movie;

import com.orehbein.graapi.api.v1.dto.producer.ProducerDto;
import com.orehbein.graapi.api.v1.dto.studio.StudioDto;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class MovieDto {

    private Long id;

    private Integer productionYear;

    private String title;

    private Boolean winner;

    private StudioDto studio;

    private Set<ProducerDto> producers = new HashSet<>();

}