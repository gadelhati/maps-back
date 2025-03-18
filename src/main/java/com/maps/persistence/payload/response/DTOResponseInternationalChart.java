package com.maps.persistence.payload.response;

import com.maps.persistence.model.ChartArea;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collection;
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
public class DTOResponseInternationalChart extends RepresentationModel<DTOResponseInternationalChart> {

    private UUID id;
    private String number;
    private String title;
    private Integer scale;
    private Collection<LocalDateTime> edition;
    private String internationalName;

    private Polygon polygon;
    private ChartArea chartArea;
}