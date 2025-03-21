package com.maps.persistence.payload.request;

import com.maps.exception.annotation.UniqueNameGaugeStation;
import com.maps.persistence.model.ChartArea;
import com.maps.persistence.model.State;
import lombok.Getter;
import org.locationtech.jts.geom.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@UniqueNameGaugeStation
public class DTORequestGaugeStation extends Identifiable {

    private String number;
    private String title;
    private Point point;

    private State state;
    private ChartArea chartArea;
}