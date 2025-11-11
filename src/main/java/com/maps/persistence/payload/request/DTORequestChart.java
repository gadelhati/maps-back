package com.maps.persistence.payload.request;

import com.maps.exception.annotation.UniqueNameChartArea;
import com.maps.persistence.model.ChartArea;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@UniqueNameChartArea(label = "number")
public record DTORequestChart (

    UUID id,
    String number,
    String title,
    Integer scale,
    Collection<LocalDateTime> edition,
    Point northEastPoint,
    Point southWestPoint,

    ChartArea chartArea
) implements DTORequestIdentifiable {}