package com.maps.persistence.payload.response;

import com.maps.persistence.model.ChartArea;
import com.maps.persistence.model.State;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;
import org.locationtech.jts.geom.*;
import org.springframework.hateoas.RepresentationModel;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@AllArgsConstructor
public class DTOResponseGaugeStation extends RepresentationModel<DTOResponseGaugeStation> {

    private UUID id;
    private String number;
    private String title;
    private Point point;

    private State state;
    private ChartArea chartArea;
}