package com.orehbein.graapi.api.v1.dto.producer;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MinMaxIntervalDto {
    List<MinMaxIntervalRecordDto> min = new ArrayList<MinMaxIntervalRecordDto>();
    List<MinMaxIntervalRecordDto> max = new ArrayList<MinMaxIntervalRecordDto>();
}
