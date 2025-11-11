package com.maps.persistence.payload.request;

import com.maps.exception.annotation.UniqueNameGaugeStation;
import com.maps.persistence.model.ChartArea;
import com.maps.persistence.model.remodel.State;

import java.util.UUID;

import org.locationtech.jts.geom.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@UniqueNameGaugeStation(label = "number")
public record DTORequestGaugeStation (

    UUID id,
    String number,
    String title,
    Point point,

    State state,
    ChartArea chartArea
) implements DTORequestIdentifiable {}
