package com.orehbein.graapi.domain.service.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import com.orehbein.graapi.core.configuration.LocalFileConfig;
import com.orehbein.graapi.domain.service.MovieService;
import com.orehbein.graapi.domain.service.ProducerService;
import com.orehbein.graapi.domain.service.StudioService;
import com.orehbein.graapi.domain.service.dto.fileloaderservice.CsvRecordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Arrays;
import java.util.List;

@Component
public class FileLoaderImplService {

    public static final char SEPARATOR = ';';
    public static final String PRODUCER_SEPARATOR = ",|\\ and ";
    public static final String WINNER_REPRESENTATION = "yes";

    private final StudioService studioService;
    
    private final ProducerService producerService;

    private final MovieService movieService;

    private final LocalFileConfig localFileConfig;

    @Autowired
    public FileLoaderImplService(final StudioService studioService, final ProducerService producerService, final MovieService movieService, final LocalFileConfig localFileConfig) {
        this.studioService = studioService;
        this.producerService = producerService;
        this.movieService = movieService;
        this.localFileConfig = localFileConfig;
    }

    @PostConstruct
    private void init(){
        final Reader reader = new InputStreamReader( this.movielistCsvInputStream(this.localFileConfig) );
        final List<CsvRecordDto> csvRecords = new CsvToBeanBuilder<CsvRecordDto>(reader)
            .withSeparator(SEPARATOR)
                .withType(CsvRecordDto.class)
                    .build().parse();

        final List<String> studioNames = this.extractStudioNames(csvRecords);
        final List<String> producerNames = this.extractProducerNames(csvRecords);

        this.studioService.createStudio(studioNames);
        this.producerService.createProducer(producerNames);

        for (CsvRecordDto csvRecordDto : csvRecords){
            this.movieService.createMovieEntity(
                csvRecordDto.getYear(),
                csvRecordDto.getTitle(),
                csvRecordDto.getStudios(),
                this.extractProducerNames(csvRecordDto.getProducers()),
                csvRecordDto.getWinner().equalsIgnoreCase(WINNER_REPRESENTATION)
            );
        }

    }

    private List<String> extractStudioNames(List<CsvRecordDto> csvRecords) {
        final List<String> studioNames = csvRecords.stream().map(csvRecord -> csvRecord.getStudios())
            .map(name -> name.trim())
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

    private List<String> extractProducerNames(String producers) {
        return Arrays.stream(producers.split(PRODUCER_SEPARATOR)).map(name -> name.trim())
            .filter(name -> StringUtils.hasText(name))
                .distinct()
                    .sorted()
                        .toList();
    }


    private InputStream movielistCsvInputStream(LocalFileConfig localFileConfig)  {
        try{
            return localFileConfig.getMovielistCsv().getInputStream();
        } catch (IOException e){
            throw new RuntimeException("Erro ao carregar arquivo");//TODO criar exception
        }
    }

}