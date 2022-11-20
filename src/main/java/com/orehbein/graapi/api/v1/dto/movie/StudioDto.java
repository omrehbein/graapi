package com.orehbein.graapi.api.v1.dto.movie;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class StudioDto {

    @NotNull
    private Long id;

    @NotNull
    private String name;
}
