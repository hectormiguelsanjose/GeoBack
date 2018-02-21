package com.biotatf.geoserver.geoback.repository;

import com.biotatf.geoserver.geoback.domain.Layer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LayerCRUDRepository extends MongoRepository<Layer,String> {

}