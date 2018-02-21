package com.biotatf.geoserver.geoback.controller;

import com.biotatf.geoserver.geoback.domain.Layer;
import com.biotatf.geoserver.geoback.service.LayerCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/layer/crud")
public class LayerCRUDController {

    private LayerCRUDService layerCRUDService;

    @Autowired
    public void setLayerCRUDService(LayerCRUDService layerCRUDService) {
        this.layerCRUDService = layerCRUDService;
    }

    // C
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public Layer create(@RequestBody Layer layer){
        return layerCRUDService.create(layer);
    }

    // R
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Layer read(@PathVariable("id") String id){
        return layerCRUDService.read(id);
    }

    // U
    @RequestMapping(value = "/",method = RequestMethod.PUT)
    public Layer update(@RequestBody Layer layer){
        return layerCRUDService.update(layer);
    }

    // D
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") String id){
        layerCRUDService.delete(id);
    }
}
