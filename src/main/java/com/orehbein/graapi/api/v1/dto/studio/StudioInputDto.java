package com.orehbein.graapi.api.v1.dto.studio;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class StudioInputDto {

    @NotNull
    private String name;
}
