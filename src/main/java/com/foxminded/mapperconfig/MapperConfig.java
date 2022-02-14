package com.foxminded.mapperconfig;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class MapperConfig {

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }
}
