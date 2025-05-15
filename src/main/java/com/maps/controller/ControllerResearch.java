package com.maps.controller;

import com.maps.persistence.model.remodel.Research;
import com.maps.persistence.payload.request.DTORequestResearch;
import com.maps.persistence.payload.response.DTOResponseResearch;
import com.maps.service.ServiceResearch;
import org.springframework.web.bind.annotation.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@RestController
@RequestMapping("/research")
public class ControllerResearch extends ControllerGeneric<Research, DTORequestResearch, DTOResponseResearch> {

    private final ServiceResearch serviceResearch;

    public ControllerResearch(ServiceResearch serviceResearch) {
        super(serviceResearch);
        this.serviceResearch = serviceResearch;
    }
    protected Class<Research> getEntityClass() {
        return Research.class;
    }
}