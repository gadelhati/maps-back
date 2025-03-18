package com.maps.persistence.payload.request;

import com.maps.exception.annotation.UniqueNameCountry;
import com.maps.persistence.model.ChartArea;
import com.maps.persistence.payload.response.DTOResponseChart;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@UniqueNameCountry
public class DTORequestChart extends Identifiable {

    private UUID id;
    private String number;
    private String title;
    private Integer scale;
    private Collection<LocalDateTime> edition;
    private Point ne;
    private Point sw;

    private ChartArea chartArea;
}