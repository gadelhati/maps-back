package com.maps.controller;

import com.maps.persistence.model.ChartArea;
import com.maps.persistence.payload.request.DTORequestChartArea;
import com.maps.persistence.payload.response.DTOResponseChartArea;
import com.maps.service.ServiceChartArea;
import org.springframework.web.bind.annotation.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@RestController
@RequestMapping("/chartArea")
public class ControllerChartArea extends ControllerGeneric<ChartArea, DTORequestChartArea, DTOResponseChartArea> {

    private final ServiceChartArea serviceChartArea;

    public ControllerChartArea(ServiceChartArea serviceChartArea) {
        super(serviceChartArea);
        this.serviceChartArea = serviceChartArea;
    }
    protected Class<ChartArea> getEntityClass() {
        return ChartArea.class;
    }
}