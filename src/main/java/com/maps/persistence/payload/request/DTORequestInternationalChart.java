package com.maps.persistence.payload.request;

import com.maps.persistence.model.ChartArea;
import org.locationtech.jts.geom.Polygon;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

public record DTORequestInternationalChart (

    UUID id,
    String number,
    String title,
    Integer scale,
    Collection<LocalDateTime> edition,
    String internationalName,

    Polygon polygon,
    ChartArea chartArea
) implements DTORequestIdentifiable {}
