package com.biotatf.geoserver.geoback.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.data.mongodb.core.geo.*;

import java.io.IOException;

public class GeoJsonCustomSerializer extends JsonSerializer<GeoJson> {

    @Override
    public void serialize(GeoJson geoJson, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeGeometry(jsonGenerator, geoJson);
    }

    public void writeGeometry(JsonGenerator jgen, GeoJson value)
            throws IOException {

        if (value instanceof GeoJsonPolygon) {
            writePolygon(jgen, (GeoJsonPolygon) value);

        } else if(value instanceof GeoJsonPoint) {
            writePoint(jgen, (GeoJsonPoint) value);

        } else if (value instanceof GeoJsonLineString) {
            writeLineString(jgen, (GeoJsonLineString) value);

        } else if (value instanceof GeoJsonMultiPoint) {
            writeMultiPoint(jgen, (GeoJsonMultiPoint) value);

        } else if (value instanceof GeoJsonMultiPolygon) {
            writeMultiPolygon(jgen, (GeoJsonMultiPolygon) value);

        }  else if (value instanceof GeoJsonMultiLineString) {
            writeMultiLineString(jgen, (GeoJsonMultiLineString) value);

        } else if (value instanceof GeoJsonGeometryCollection) {
            writeGeometryCollection(jgen, (GeoJsonGeometryCollection) value);

        } else {
            throw new UnsupportedOperationException("not implemented: "
                    + value.getClass().getName());
        }
    }

    private void writeGeometryCollection(JsonGenerator jgen, GeoJsonGeometryCollection value) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("type", "GeometryCollection");
        jgen.writeArrayFieldStart("geometries");

        value.getCoordinates().forEach(geom -> {
            try {
                writeGeometry(jgen,geom);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        jgen.writeEndArray();
        jgen.writeEndObject();
    }

    private void writeMultiPoint(JsonGenerator jgen, GeoJsonMultiPoint value)
            throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("type", "MultiPoint");
        jgen.writeArrayFieldStart("coordinates");

        for (int i = 0; i != value.getCoordinates().size(); ++i) {
            writePointCoords(jgen, new GeoJsonPoint(value.getCoordinates().get(i)));
        }

        jgen.writeEndArray();
        jgen.writeEndObject();
    }

    private void writeMultiLineString(JsonGenerator jgen, GeoJsonMultiLineString value)
            throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("type", "MultiLineString");
        jgen.writeArrayFieldStart("coordinates");

        value.getCoordinates().forEach(line -> {
            try {
                writeLineStringCoords(jgen,line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        jgen.writeEndArray();
        jgen.writeEndObject();
    }


    private void writeMultiPolygon(JsonGenerator jgen, GeoJsonMultiPolygon value)
            throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("type", "MultiPolygon");
        jgen.writeArrayFieldStart("coordinates");

        for (int i = 0; i != value.getCoordinates().size(); ++i) {
            writePolygonCoordinates(jgen, value.getCoordinates().get(i));
        }

        jgen.writeEndArray();
        jgen.writeEndObject();
    }

    private void writePolygon(JsonGenerator jgen, GeoJsonPolygon value)
            throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("type", "Polygon");
        jgen.writeFieldName("coordinates");
        writePolygonCoordinates(jgen, value);

        jgen.writeEndObject();
    }

    private void writePolygonCoordinates(JsonGenerator jgen, GeoJsonPolygon value)
            throws IOException {
        jgen.writeStartArray();

        for (int i = 0; i != value.getCoordinates().size(); ++i) {
            writeLineStringCoords(jgen, value.getCoordinates().get(i));
        }
        jgen.writeEndArray();
    }

    private void writeLineStringCoords(JsonGenerator jgen, GeoJsonLineString ring)
            throws IOException {
        jgen.writeStartArray();
        for (int i = 0; i != ring.getCoordinates().size(); ++i) {

            GeoJsonPoint p = new GeoJsonPoint(ring.getCoordinates().get(i));
            writePointCoords(jgen, p);
        }
        jgen.writeEndArray();
    }

    private void writeLineString(JsonGenerator jgen, GeoJsonLineString lineString)
            throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("type", "LineString");
        jgen.writeFieldName("coordinates");
        writeLineStringCoords(jgen, lineString);
        jgen.writeEndObject();
    }

    private void writePoint(JsonGenerator jgen, GeoJsonPoint p)
            throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("type", "Point");
        jgen.writeFieldName("coordinates");
        writePointCoords(jgen, p);
        jgen.writeEndObject();
    }

    private void writePointCoords(JsonGenerator jgen, GeoJsonPoint p)
            throws IOException {
        jgen.writeStartArray();
        jgen.writeNumber(((float)(int)(1000000 * p.getX()) / 1000000));
        jgen.writeNumber(((float)(int)(1000000 * p.getY()) / 1000000));
        jgen.writeEndArray();
    }

}
