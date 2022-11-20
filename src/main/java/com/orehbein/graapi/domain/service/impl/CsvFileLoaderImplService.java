package com.orehbein.graapi.domain.service.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import com.orehbein.graapi.core.properties.RessorceFileProperties;
import com.orehbein.graapi.domain.service.MovieService;
import com.orehbein.graapi.domain.service.ProducerService;
import com.orehbein.graapi.domain.service.StudioService;
import com.orehbein.graapi.domain.service.dto.fileloaderservice.CsvRecordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.*;
import java.util.Arrays;
import java.util.List;

@Component
public class CsvFileLoaderImplService {

    public static final char SEPARATOR = ';';
    public static final String PRODUCER_SEPARATOR = ",|\\ and ";

    public static final String STUDIO_SEPARATOR = ",";
    public static final String WINNER_REPRESENTATION = "yes";

    private final StudioService studioService;
    
    private final ProducerService producerService;

    private final MovieService movieService;

    private final RessorceFileProperties localFileConfig;

    @Autowired
    public CsvFileLoaderImplService(final StudioService studioService, final ProducerService producerService, final MovieService movieService, final RessorceFileProperties localFileConfig) {
        this.studioService = studioService;
        this.producerService = producerService;
        this.movieService = movieService;
        this.localFileConfig = localFileConfig;
    }

    @PostConstruct
    @Transactional
    private void init(){
        final Reader reader = new InputStreamReader( this.movielistCsvInputStream(this.localFileConfig) );
        final List<CsvRecordDto> csvRecords = new CsvToBeanBuilder<CsvRecordDto>(reader)
            .withSeparator(SEPARATOR)
                .withType(CsvRecordDto.class)
                    .build().parse();

        final List<String> studioNames = this.extractStudioNames(csvRecords);
        final List<String> producerNames = this.extractProducerNames(csvRecords);

        this.studioService.create(studioNames);
        this.producerService.create(producerNames);

        for (CsvRecordDto csvRecordDto : csvRecords){
            this.movieService.create(
                csvRecordDto.getYear(),
                csvRecordDto.getTitle(),
                this.extractStudioNames(csvRecordDto.getStudios()),
                this.extractProducerNames(csvRecordDto.getProducers()),
                csvRecordDto.getWinner().equalsIgnoreCase(WINNER_REPRESENTATION)
            );
        }

    }

    private List<String> extractStudioNames(List<CsvRecordDto> csvRecords) {
        final List<String> studioNames = csvRecords.stream().flatMap(
            csvRecord -> this.extractStudioNames(csvRecord.getStudios()).stream()
        ).map(name -> name.trim())
            .distinct()
                .sorted()
                    .toList();
        return studioNames;
    }

    private List<String> extractProducerNames(List<CsvRecordDto> csvRecords) {
        final List<String> producerNames = csvRecords.stream().flatMap(
            csvRecord -> this.extractProducerNames(csvRecord.getProducers()).stream()
        ).map(name -> name.trim())
            .filter(name -> StringUtils.hasText(name))
                .distinct()
                    .sorted()
                        .toList();
        return producerNames;
    }
    private List<String> extractStudioNames(String names) {
        return Arrays.stream(names.split(STUDIO_SEPARATOR)).map(name -> name.trim())
            .filter(name -> StringUtils.hasText(name))
                .distinct()
                    .sorted()
                        .toList();
    }

    private List<String> extractProducerNames(String names) {
        return Arrays.stream(names.split(PRODUCER_SEPARATOR)).map(name -> name.trim())
            .filter(name -> StringUtils.hasText(name))
                .distinct()
                    .sorted()
                        .toList();
    }



    private InputStream movielistCsvInputStream(RessorceFileProperties localFileConfig)  {
        try{
            return localFileConfig.getMovielistCsv().getInputStream();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}