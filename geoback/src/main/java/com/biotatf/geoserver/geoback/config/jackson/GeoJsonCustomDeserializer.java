package com.biotatf.geoserver.geoback.config.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GeoJsonCustomDeserializer extends JsonDeserializer<GeoJson> {

    @Override
    public GeoJson deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.readValueAsTree();
        return doDeserialize(node);

    }

    private GeoJson doDeserialize(JsonNode node){

        JsonNode coordinates = node.get("coordinates");
        String typeName = node.get("type").asText();
        if (coordinates != null && coordinates.isArray()) {

            if (typeName.equals("Point")) {
                return doDeserializeAsGeoJsonPoint((ArrayNode) coordinates);

            } else if(typeName.equals("MultiPoint")) {
                return doDeserializeAsGeoJsonMultiPoint((ArrayNode) coordinates);

            } else if(typeName.equals("LineString")) {
                return doDeserializeAsGeoJsonLineString((ArrayNode) coordinates);

            } else if (typeName.equals("MultiLineString")) {
                return doDeserializeAsGeoJsonMultiLineString((ArrayNode) coordinates);

            } else if(typeName.equals("Polygon")) {
                return doDeserializeAsGeoJsonPolygon((ArrayNode) coordinates);

            } else if (typeName.equals("MultiPolygon")) {
                return doDeserializeAsGeoJsonMultiPolygon((ArrayNode) coordinates);

            }
        }else if (typeName.equals("GeometryCollection")) {
            JsonNode geometries = node.get("geometries");
            return doDeserializeAsGeoJsonGeometryCollection((ArrayNode) geometries);
        } else {
            throw new UnsupportedOperationException();
        }

        return null;
    }

    protected GeoJsonPoint toGeoJsonPoint(ArrayNode node) {

        if (node == null) {
            return null;
        }

        return new GeoJsonPoint(node.get(0).asDouble(), node.get(1).asDouble());
    }


    protected Point toPoint(ArrayNode node) {

        if (node == null) {
            return null;
        }

        return new Point(node.get(0).asDouble(), node.get(1).asDouble());
    }


    protected List<Point> toPoints(ArrayNode node) {

        if (node == null) {
            return Collections.emptyList();
        }

        List<Point> points = new ArrayList<Point>(node.size());

        for (JsonNode coordinatePair : node) {
            if (coordinatePair.isArray()) {
                points.add(toPoint((ArrayNode) coordinatePair));
            }
        }
        return points;
    }

    protected GeoJsonLineString toLineString(ArrayNode node) {
        return new GeoJsonLineString(toPoints(node));
    }

    private GeoJsonPoint doDeserializeAsGeoJsonPoint(ArrayNode coordinates){
        return toGeoJsonPoint(coordinates);
    }

    private GeoJsonLineString doDeserializeAsGeoJsonLineString(ArrayNode coordinates){
        return new GeoJsonLineString(toPoints(coordinates));
    }

    private GeoJsonPolygon doDeserializeAsGeoJsonPolygon(ArrayNode coordinates){
        for (JsonNode ring : coordinates) {

            return new GeoJsonPolygon(toPoints((ArrayNode) ring));
        }

        return null;
    }

    private GeoJsonMultiPoint doDeserializeAsGeoJsonMultiPoint(ArrayNode coordinates){
        return new GeoJsonMultiPoint(toPoints(coordinates));
    }

    private GeoJsonMultiLineString doDeserializeAsGeoJsonMultiLineString(ArrayNode coordinates){
        List<GeoJsonLineString> lines = new ArrayList<GeoJsonLineString>(coordinates.size());

        for (JsonNode lineString : coordinates) {
            if (lineString.isArray()) {
                lines.add(toLineString((ArrayNode) lineString));
            }
        }

        return new GeoJsonMultiLineString(lines);
    }

    private GeoJsonMultiPolygon doDeserializeAsGeoJsonMultiPolygon(ArrayNode coordinates){
        List<GeoJsonPolygon> polygones = new ArrayList<GeoJsonPolygon>(coordinates.size());

        for (JsonNode polygon : coordinates) {
            for (JsonNode ring : polygon) {
                polygones.add(new GeoJsonPolygon(toPoints((ArrayNode) ring)));
            }
        }

        return new GeoJsonMultiPolygon(polygones);
    }

    private GeoJsonGeometryCollection doDeserializeAsGeoJsonGeometryCollection(ArrayNode geometries){
        // Multiple collection
        List<GeoJson<?>> items = new ArrayList<>();
        geometries.forEach(g -> items.add(doDeserialize(g)));
        GeoJsonGeometryCollection geometryCollection = new GeoJsonGeometryCollection(items);
        return geometryCollection;

    }

}
