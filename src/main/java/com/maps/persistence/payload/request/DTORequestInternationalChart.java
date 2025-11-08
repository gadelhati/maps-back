package com.maps.persistence.payload.request;

import com.maps.persistence.model.ChartArea;
import lombok.Getter;
import org.locationtech.jts.geom.Polygon;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Getter
public class DTORequestInternationalChart extends DTORequestIdentifiable {

    private String number;
    private String title;
    private Integer scale;
    private Collection<LocalDateTime> edition;
    private String internationalName;

    private Polygon polygon;
    private ChartArea chartArea;
}
