package com.biotatf.geoserver.geoback.converter;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiLineString;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hector on 27/2/17.
 */
@Component
public class DBObjectToGeoJsonMultiLineStringConverter extends AbstractGeoJsonConverter {

    @Override
    public GeoJsonMultiLineString convert(DBObject dbObject) {
        List<GeoJsonLineString> lineStrings = new ArrayList<>();
        BasicDBList coordinates = (BasicDBList) dbObject.get("coordinates");
        List<Point> pointList = new ArrayList<>();
        if (coordinates.size() >= 0){
            Iterator it = coordinates.iterator();
            while (it.hasNext()){
                BasicDBList coordinate = (BasicDBList)it.next();
                coordinate.forEach(o -> pointList.add(createPoint((BasicDBList)o)));
                lineStrings.add(new GeoJsonLineString(pointList));
                pointList.clear();
            }

        }
        return new GeoJsonMultiLineString(lineStrings);
    }
}
