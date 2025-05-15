package com.maps.controller;

import com.maps.persistence.model.remodel.Researcher;
import com.maps.persistence.payload.request.DTORequestResearcher;
import com.maps.persistence.payload.response.DTOResponseResearcher;
import com.maps.service.ServiceResearcher;
import org.springframework.web.bind.annotation.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@RestController
@RequestMapping("/researcher")
public class ControllerResearcher extends ControllerGeneric<Researcher, DTORequestResearcher, DTOResponseResearcher> {

    private final ServiceResearcher serviceResearcher;

    public ControllerResearcher(ServiceResearcher serviceResearcher) {
        super(serviceResearcher);
        this.serviceResearcher = serviceResearcher;
    }
    protected Class<Researcher> getEntityClass() {
        return Researcher.class;
    }
}