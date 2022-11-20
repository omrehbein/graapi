package com.orehbein.graapi.core.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {
	@Bean
    public ObjectMapper objectMapper() {
		
		ObjectMapper mapper = new ObjectMapper();
	    
        mapper
        .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT )
        .enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY )
        .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        mapper.registerModule(new JavaTimeModule());

        //or
        //JsonMapper jsonMapper = new JsonMapper();
        //jsonMapper.registerModule(new JavaTimeModule());

        return mapper;

	}
}
