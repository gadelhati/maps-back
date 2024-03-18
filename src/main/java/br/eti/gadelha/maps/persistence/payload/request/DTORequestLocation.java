package br.eti.gadelha.maps.persistence.payload.request;

import br.eti.gadelha.maps.exception.annotation.UniqueNameCountry;
import lombok.Getter;
import org.locationtech.jts.geom.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @UniqueNameCountry
public class DTORequestLocation {

    private UUID id;
    private LocalDateTime localDateTime;
    private Point point;
    private LineString lineString;
    private Polygon polygon;
    private MultiPolygon multiPolygon;
}