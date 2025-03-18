package com.maps.controller;

import com.maps.persistence.model.Country;
import com.maps.persistence.payload.request.DTORequestCountry;
import com.maps.persistence.payload.response.DTOResponseCountry;
import com.maps.service.ServiceCountry;
import org.springframework.web.bind.annotation.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@RestController
@RequestMapping("/country")
public class ControllerCountry extends ControllerGeneric<Country, DTORequestCountry, DTOResponseCountry> {

    private final ServiceCountry serviceCountry;

    public ControllerCountry(ServiceCountry serviceCountry) {
        super(serviceCountry);
        this.serviceCountry = serviceCountry;
    }
    protected Class<Country> getEntityClass() {
        return Country.class;
    }
}