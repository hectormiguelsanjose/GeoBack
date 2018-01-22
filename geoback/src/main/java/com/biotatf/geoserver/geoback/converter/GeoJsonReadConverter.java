package com.biotatf.geoserver.geoback.converter;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.geo.GeoJsonGeometryCollection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@ReadingConverter
public class GeoJsonReadConverter implements Converter<DBObject,GeoJson> {


    private HashMap<String, GeoJsonConverter> converters;

    public GeoJsonReadConverter() {
        this.converters = configConverters();
    }

    @Override
    public GeoJson convert(DBObject dbObject) {
        GeoJson geoJson;

        String type = (String) dbObject.get("type");

        if (type.equals("GeometryCollection")){
            geoJson = getGeoJsonFromGeometryCollection(dbObject);
        }else{
            geoJson = converters.get(type).convert(dbObject);
        }

        return geoJson;
    }



    private GeoJson getGeoJsonFromGeometryCollection(DBObject dbObject) {
        // Multiple collection
        GeoJson geoJson;
        BasicDBList geometryCollection = (BasicDBList) dbObject.get("geometries");
        List<GeoJson<?>> geoJsonList = new ArrayList<>();
        geometryCollection.forEach(g -> {
            DBObject geometry = (DBObject) g;
            geoJsonList.add(converters.get(geometry.get("type")).convert(geometry));
        });

        geoJson = new GeoJsonGeometryCollection(geoJsonList);
        return geoJson;
    }

    private HashMap<String, GeoJsonConverter> configConverters() {
        HashMap<String,GeoJsonConverter> converters = new HashMap<>();
        converters.put("Point",new DBObjectToGeoJsonPointConverter());
        converters.put("LineString",new DBObjectToGeoJsonLineStringConverter());
        converters.put("Polygon",new DBObjectToGeoJsonPolygonConverter());
        converters.put("MultiPoint",new DBObjectToGeoJsonMultiPointConverter());
        converters.put("MultiLineString",new DBObjectToGeoJsonMultiLineStringConverter());
        converters.put("MultiPolygon",new DBObjectToGeoJsonMultiPolygonConverter());
        return converters;
    }
}
