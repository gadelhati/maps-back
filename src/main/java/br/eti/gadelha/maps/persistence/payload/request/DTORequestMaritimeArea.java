package br.eti.gadelha.maps.persistence.payload.request;

import br.eti.gadelha.maps.exception.annotation.UniqueNameMaritimeArea;
import lombok.Getter;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;

import java.util.UUID;

@Getter @UniqueNameMaritimeArea
public class DTORequestMaritimeArea {

    private UUID id;
    private String code;
    private String name;
    private String start;
    private String finish;
    private Polygon polygon;
    private MultiPolygon multiPolygon;
}