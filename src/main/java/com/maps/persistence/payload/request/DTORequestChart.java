package com.maps.persistence.payload.request;

import com.maps.exception.annotation.UniqueNameChartArea;
import com.maps.persistence.model.ChartArea;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Getter
@UniqueNameChartArea(label = "number")
public class DTORequestChart extends DTORequestIdentifiable {

    private String number;
    private String title;
    private Integer scale;
    private Collection<LocalDateTime> edition;
    private Point northEastPoint;
    private Point southWestPoint;

    private ChartArea chartArea;
}
