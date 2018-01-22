package com.biotatf.geoserver.geoback.domain;

import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "layers")
public class Layer extends AbstractDomainObject{

    private String token;
    private String name;
    private String series;
    private String description;
    private FeatureCollection geoData;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FeatureCollection getGeoData() {
        return geoData;
    }

    public void setGeoData(FeatureCollection geoData) {
        this.geoData = geoData;
    }
}
