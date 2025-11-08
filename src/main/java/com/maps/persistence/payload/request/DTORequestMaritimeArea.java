package com.maps.persistence.payload.request;

import com.maps.exception.annotation.UniqueNameMaritimeArea;
import lombok.Getter;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Getter
@UniqueNameMaritimeArea(label = "name")
public class DTORequestMaritimeArea extends DTORequestIdentifiable {

    private String code;
    private String name;
    private String start;
    private String finish;
    private Polygon polygon;
    private MultiPolygon multiPolygon;
}
