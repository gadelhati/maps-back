package com.maps.controller;

import com.maps.persistence.model.MaritimeArea;
import com.maps.persistence.payload.request.DTORequestMaritimeArea;
import com.maps.persistence.payload.response.DTOResponseMaritimeArea;
import com.maps.service.ServiceMaritimeArea;
import org.springframework.web.bind.annotation.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@RestController
@RequestMapping("/maritimeArea")
public class ControllerMaritimeArea extends ControllerGeneric<MaritimeArea, DTORequestMaritimeArea, DTOResponseMaritimeArea> {

    private final ServiceMaritimeArea serviceMaritimeArea;

    public ControllerMaritimeArea(ServiceMaritimeArea serviceMaritimeArea) {
        super(serviceMaritimeArea);
        this.serviceMaritimeArea = serviceMaritimeArea;
    }
    protected Class<MaritimeArea> getEntityClass() {
        return MaritimeArea.class;
    }
}