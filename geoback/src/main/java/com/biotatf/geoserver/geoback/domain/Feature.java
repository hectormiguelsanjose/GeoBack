package com.biotatf.geoserver.geoback.domain;


import org.springframework.data.mongodb.core.geo.GeoJson;

import java.util.HashMap;

public class Feature {

    private String type;

    //@JsonSerialize(using = GeoJsonCustomSerializer.class)
    //@JsonDeserialize(using = GeoJsonCustomDeserializer.class)
    private GeoJson geometry;

    private HashMap<String,?> properties = new HashMap<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GeoJson getGeometry() {
        return geometry;
    }

    public void setGeometry(GeoJson geometry) {
        this.geometry = geometry;
    }

    public HashMap<String, ?> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<String, ?> properties) {
        this.properties = properties;
    }
}