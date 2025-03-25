package com.maps.persistence.payload.request;

import com.maps.exception.annotation.UniqueNameCountry;
import com.maps.persistence.model.ChartArea;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
public class DTORequestChart extends Identifiable {

    private String number;
    private String title;
    private Integer scale;
    private Collection<LocalDateTime> edition;
    private Point ne;
    private Point sw;

    private ChartArea chartArea;
}