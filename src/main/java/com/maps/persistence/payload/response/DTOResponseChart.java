package com.maps.persistence.payload.response;

import com.maps.persistence.model.ChartArea;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.locationtech.jts.geom.Point;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@AllArgsConstructor
public class DTOResponseChart extends RepresentationModel<DTOResponseChart> {

    private UUID id;
    private String number;
    private String title;
    private Integer scale;
    private Collection<LocalDateTime> edition;
    private Point northEastPoint;
    private Point southWestPoint;

    private ChartArea chartArea;
}