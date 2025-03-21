package com.maps.persistence.payload.request;

import com.maps.exception.annotation.UniqueNameMaritimeArea;
import lombok.Getter;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@UniqueNameMaritimeArea
public class DTORequestMaritimeArea extends Identifiable {

    private String code;
    private String name;
    private String start;
    private String finish;
    private Polygon polygon;
    private MultiPolygon multiPolygon;
}