package com.maps.controller;

import com.maps.persistence.model.remodel.City;
import com.maps.persistence.payload.request.DTORequestCity;
import com.maps.persistence.payload.response.DTOResponseCity;
import com.maps.service.ServiceCity;
import org.springframework.web.bind.annotation.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@RestController
@RequestMapping("/city")
public class ControllerCity extends ControllerGeneric<City, DTORequestCity, DTOResponseCity> {

    private final ServiceCity serviceCity;

    public ControllerCity(ServiceCity serviceCity) {
        super(serviceCity);
        this.serviceCity = serviceCity;
    }
    protected Class<City> getEntityClass() {
        return City.class;
    }
}