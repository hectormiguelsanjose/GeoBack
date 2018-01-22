package com.biotatf.geoserver.geoback.config.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.data.mongodb.core.geo.GeoJson;


public class GeoJsonCustomModule extends SimpleModule{

    public GeoJsonCustomModule() {

        addSerializer(GeoJson.class, new GeoJsonCustomSerializer());
        addDeserializer(GeoJson.class, new GeoJsonCustomDeserializer());
    }
}