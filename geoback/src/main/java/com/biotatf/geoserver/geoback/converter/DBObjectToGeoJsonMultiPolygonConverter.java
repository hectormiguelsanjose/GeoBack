package com.biotatf.geoserver.geoback.converter;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hector on 27/2/17.
 */
@Component
public class DBObjectToGeoJsonMultiPolygonConverter extends AbstractGeoJsonConverter implements GeoJsonConverter {

    @Override
    public GeoJsonMultiPolygon convert(DBObject dbObject) {
        GeoJsonMultiPolygon multiPolygon = null;
        BasicDBList coordinates = (BasicDBList) dbObject.get("coordinates");
        List<GeoJsonPolygon> polygons = new ArrayList<>();
        List<Point> pointList = new ArrayList<>();
        if (coordinates.size() >= 0) {
            Iterator it = coordinates.iterator();
            while (it.hasNext()){
                // poligono
                GeoJsonPolygon geoJsonPolygon = null;
                BasicDBList polygon = (BasicDBList) it.next();
                Iterator itit = polygon.iterator();
                while (itit.hasNext()){
                    BasicDBList coordinate = (BasicDBList)itit.next();
                    coordinate.forEach(o -> pointList.add(createPoint((BasicDBList) o)));
                    if (geoJsonPolygon == null){
                        geoJsonPolygon = new GeoJsonPolygon(pointList);
                        pointList.clear();
                    }else{
                        geoJsonPolygon = geoJsonPolygon.withInnerRing(pointList);
                        pointList.clear();
                    }
                }
                polygons.add(geoJsonPolygon);

            }
        }

        multiPolygon = new GeoJsonMultiPolygon(polygons);
        return multiPolygon;
    }
}
