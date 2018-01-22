package com.biotatf.geoserver.geoback.converter;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hector on 27/2/17.
 */
@Component
public class DBObjectToGeoJsonPolygonConverter extends AbstractGeoJsonConverter {

    @Override
    public GeoJsonPolygon convert(DBObject dbObject) {
        GeoJsonPolygon polygon = null;
        BasicDBList coordinates = (BasicDBList) dbObject.get("coordinates");
        List<Point> pointList = new ArrayList<>();
        if (coordinates.size() >= 0){
            Iterator it = coordinates.iterator();
            while (it.hasNext()){
                BasicDBList coordinate = (BasicDBList)it.next();
                coordinate.forEach(o -> pointList.add(createPoint((BasicDBList) o)));
                if (polygon == null){
                    polygon = new GeoJsonPolygon(pointList);
                    pointList.clear();
                }else{
                    polygon = polygon.withInnerRing(pointList);
                    pointList.clear();
                }


            }

        }
        return polygon;
    }
}
