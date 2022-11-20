package com.orehbein.graapi.api.v1.dto.producer;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProducerDto {

    @NotNull
    private Long id;

    @NotNull
    private String name;
}
