package com.biotatf.geoserver.geoback.service;


import com.biotatf.geoserver.geoback.domain.Layer;

public interface LayerCRUDService {

    // C
    Layer create(Layer layer);
    // R
    Layer read(String id);
    // U
    Layer update(Layer layer);
    // D
    void delete(String id);
}
