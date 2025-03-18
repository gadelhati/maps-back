package com.maps.persistence.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@AllArgsConstructor
public class DTOResponseMaritimeArea extends RepresentationModel<DTOResponseMaritimeArea> {

    private UUID id;
    private String code;
    private String name;
    private String start;
    private String finish;
    private Polygon polygon;
    private MultiPolygon multiPolygon;
}