package br.eti.gadelha.maps.persistence.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;

import java.util.UUID;

@Getter @AllArgsConstructor
public class DTOResponseMaritimeArea {

    private UUID id;
    private String code;
    private String name;
    private String start;
    private String finish;
    private Polygon polygon;
    private MultiPolygon multiPolygon;
}