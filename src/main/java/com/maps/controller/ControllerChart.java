package com.maps.controller;

import com.maps.persistence.model.Chart;
import com.maps.persistence.payload.request.DTORequestChart;
import com.maps.persistence.payload.response.DTOResponseChart;
import com.maps.service.ServiceChart;
import org.springframework.web.bind.annotation.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@RestController
@RequestMapping("/chart")
public class ControllerChart extends ControllerGeneric<Chart, DTORequestChart, DTOResponseChart> {

    private final ServiceChart serviceChart;

    public ControllerChart(ServiceChart serviceChart) {
        super(serviceChart);
        this.serviceChart = serviceChart;
    }
    protected Class<Chart> getEntityClass() {
        return Chart.class;
    }
}