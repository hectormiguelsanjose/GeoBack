package com.biotatf.geoserver.geoback.service;

import com.biotatf.geoserver.geoback.domain.Layer;
import com.biotatf.geoserver.geoback.repository.LayerCRUDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LayerCRUDServiceImpl implements LayerCRUDService{

    private LayerCRUDRepository layerCRUDRepository;

    @Autowired
    public void setLayerCRUDRepository(LayerCRUDRepository layerCRUDRepository) {
        this.layerCRUDRepository = layerCRUDRepository;
    }

    @Override
    public Layer create(Layer layer) {
        return layerCRUDRepository.save(layer);
    }

    @Override
    public Layer read(String id) {
        return layerCRUDRepository.findOne(id);
    }

    @Override
    public Layer update(Layer layer) {
        return layerCRUDRepository.save(layer);
    }

    @Override
    public void delete(String id) {
        layerCRUDRepository.delete(id);
    }
}
