package com.biotatf.geoserver.geoback.converter;

import com.mongodb.DBObject;
import org.springframework.data.mongodb.core.geo.GeoJson;

/**
 * Created by Hector on 27/02/2017.
 */
public interface GeoJsonConverter{
    GeoJson convert(DBObject dbObject);
}
