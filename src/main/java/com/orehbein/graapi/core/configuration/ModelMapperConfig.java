package com.orehbein.graapi.core.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
	    
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	    
	    //EspecialMapper.map(modelMapper);
	    
		return modelMapper;
	}
}

