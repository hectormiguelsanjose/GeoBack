package com.biotatf.geoserver.geoback.converter;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPoint;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hector on 27/2/17.
 */
@Component
public class DBObjectToGeoJsonMultiPointConverter extends AbstractGeoJsonConverter {

    @Override
    public GeoJsonMultiPoint convert(DBObject dbObject) {
        BasicDBList coordinates = (BasicDBList) dbObject.get("coordinates");
        List<Point> pointList = new ArrayList<>();
        if (coordinates.size() >= 0) {
            Iterator it = coordinates.iterator();
            while (it.hasNext()) {
                pointList.add(createPoint((BasicDBList) it.next()));
            }

        }
        return new GeoJsonMultiPoint(pointList);
    }
}
