package com.maps.controller;

import com.maps.persistence.model.InternationalChart;
import com.maps.persistence.payload.request.DTORequestInternationalChart;
import com.maps.persistence.payload.response.DTOResponseInternationalChart;
import com.maps.service.ServiceInternationalChart;
import org.springframework.web.bind.annotation.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@RestController
@RequestMapping("/internationalChart")
public class ControllerInternationalChart extends ControllerGeneric<InternationalChart, DTORequestInternationalChart, DTOResponseInternationalChart> {

    private final ServiceInternationalChart serviceInternationalChart;

    public ControllerInternationalChart(ServiceInternationalChart serviceInternationalChart) {
        super(serviceInternationalChart);
        this.serviceInternationalChart = serviceInternationalChart;
    }
    protected Class<InternationalChart> getEntityClass() {
        return InternationalChart.class;
    }
}