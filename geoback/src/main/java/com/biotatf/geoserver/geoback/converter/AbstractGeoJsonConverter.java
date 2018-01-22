package com.biotatf.geoserver.geoback.converter;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJson;


public abstract class AbstractGeoJsonConverter implements GeoJsonConverter{


    protected Point createPoint(BasicDBList coordinate){
        Double longitude = (Double) coordinate.get(0);
        Double latitude = (Double) coordinate.get(1);
        Point point = new Point(longitude,latitude);
        return point;
    }

    public abstract GeoJson convert(DBObject dbObject);

}
