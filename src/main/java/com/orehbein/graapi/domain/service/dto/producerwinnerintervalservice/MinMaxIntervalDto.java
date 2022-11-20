package com.orehbein.graapi.domain.service.dto.producerwinnerintervalservice;

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
