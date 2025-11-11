package com.maps.persistence.payload.request;

import com.maps.exception.annotation.UniqueNameMaritimeArea;

import java.util.UUID;

import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@UniqueNameMaritimeArea(label = "name")
public record DTORequestMaritimeArea (

    UUID id,
    String code,
    String name,
    String start,
    String finish,

    Polygon polygon,
    MultiPolygon multiPolygon
) implements DTORequestIdentifiable {}
