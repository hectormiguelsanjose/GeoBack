package com.biotatf.geoserver.geoback.domain;


/**
 * Created by Hector on 25/05/2017.
 */
public class FeatureCollection {
    private final String type = "FeatureCollection";
    private Feature[] features;

    public String getType() {
        return type;
    }

    public Feature[] getFeatures() {
        return features;
    }

    public void setFeatures(Feature[] features) {
        this.features = features;
    }

}
