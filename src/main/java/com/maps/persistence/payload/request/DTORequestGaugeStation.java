package com.maps.persistence.payload.request;

import com.maps.exception.annotation.UniqueNameGaugeStation;
import com.maps.persistence.model.ChartArea;
import com.maps.persistence.model.remodel.State;
import lombok.Getter;
import org.locationtech.jts.geom.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Getter
@UniqueNameGaugeStation(label = "number")
public class DTORequestGaugeStation extends DTORequestIdentifiable {

    private String number;
    private String title;
    private Point point;

    private State state;
    private ChartArea chartArea;
}
