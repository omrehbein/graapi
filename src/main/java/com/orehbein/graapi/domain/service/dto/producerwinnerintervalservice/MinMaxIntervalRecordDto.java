package com.orehbein.graapi.domain.service.dto.producerwinnerintervalservice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MinMaxIntervalRecordDto {
    private String producer;

    private Integer interval;

    private Integer previousWin;

    private Integer followingWin;
}
