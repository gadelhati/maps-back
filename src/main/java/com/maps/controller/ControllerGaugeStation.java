package com.maps.controller;

import com.maps.persistence.model.GaugeStation;
import com.maps.persistence.payload.request.DTORequestGaugeStation;
import com.maps.persistence.payload.response.DTOResponseGaugeStation;
import com.maps.service.ServiceGaugeStation;
import org.springframework.web.bind.annotation.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@RestController
@RequestMapping("/gaugeStation")
public class ControllerGaugeStation extends ControllerGeneric<GaugeStation, DTORequestGaugeStation, DTOResponseGaugeStation> {

    private final ServiceGaugeStation serviceGaugeStation;

    public ControllerGaugeStation(ServiceGaugeStation serviceGaugeStation) {
        super(serviceGaugeStation);
        this.serviceGaugeStation = serviceGaugeStation;
    }
    protected Class<GaugeStation> getEntityClass() {
        return GaugeStation.class;
    }
}