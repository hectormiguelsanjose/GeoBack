package com.biotatf.geoserver.geoback.config;

import com.biotatf.geoserver.geoback.config.jackson.GeoJsonCustomModule;
import com.fasterxml.jackson.databind.Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("com.biotatf.geoserver.geoback.repository")
public class ApplicationConfig {


    @Bean
    public Module getGeoJsonCustomModule(){
        return new GeoJsonCustomModule();
    }
}
