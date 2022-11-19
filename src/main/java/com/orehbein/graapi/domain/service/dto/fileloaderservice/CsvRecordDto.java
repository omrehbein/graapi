package com.orehbein.graapi.domain.service.dto.fileloaderservice;

import lombok.Data;

@Data
public class CsvRecordDto {
    private Integer year;

    private String title;

    private String studios;

    private String producers;

    private String winner;
}
