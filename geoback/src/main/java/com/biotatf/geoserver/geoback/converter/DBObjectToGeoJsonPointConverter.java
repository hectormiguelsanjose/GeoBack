package com.biotatf.geoserver.geoback.converter;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Component;

/**
 * Created by hector on 27/2/17.
 */
@Component
public class DBObjectToGeoJsonPointConverter extends AbstractGeoJsonConverter {

    @Override
    public GeoJsonPoint convert(DBObject dbObject) {
        return new GeoJsonPoint(createPoint((BasicDBList) dbObject.get("coordinates")));
    }
}
