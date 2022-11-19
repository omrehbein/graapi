package com.orehbein.graapi.core.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
@Data
@Configuration
public class LocalFileConfig {

    @Value("classpath:movielist.csv")
    private Resource movielistCsv;
}
