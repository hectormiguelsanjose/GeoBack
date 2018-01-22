package com.biotatf.geoserver.geoback.config;

import com.biotatf.geoserver.geoback.converter.GeoJsonReadConverter;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.singletonList;

@Configuration
@Profile("production")
public class GeoSpatialAppConfigProd extends AbstractMongoConfiguration {

    @Value("${biotatf.basepackage}")
    String basePackage;

    @Value("${spring.data.mongodb.host}")
    private String host;
    @Value("${spring.data.mongodb.port}")
    private Integer port;
    @Value("${spring.data.mongodb.username}")
    private String username;
    @Value("${spring.data.mongodb.database}")
    private String database;
    @Value("${spring.data.mongodb.password}")
    private String password;


    @Bean
    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(singletonList(new ServerAddress(host, port)),
                singletonList(MongoCredential.createCredential(username,database, password.toCharArray())));
    }


    @Override
    public String getDatabaseName()
    {
       return database;
    }

    @Override
    public Collection<String> getMappingBasePackages() {
        Collection<String> basePackages = new ArrayList<String>();
        basePackages.add(basePackage);
        return  basePackages;

    }

    @Bean
    @Override
    public CustomConversions customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<Converter<?, ?>>();
        converterList.add(new GeoJsonReadConverter());
        return new CustomConversions(converterList);
    }



}
