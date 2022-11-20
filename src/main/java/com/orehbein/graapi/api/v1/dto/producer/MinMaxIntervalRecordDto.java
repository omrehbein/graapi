package com.orehbein.graapi.api.v1.dto.producer;

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
