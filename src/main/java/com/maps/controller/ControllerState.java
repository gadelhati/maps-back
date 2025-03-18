package com.maps.controller;

import com.maps.persistence.model.State;
import com.maps.persistence.payload.request.DTORequestState;
import com.maps.persistence.payload.response.DTOResponseState;
import com.maps.service.ServiceState;
import org.springframework.web.bind.annotation.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@RestController
@RequestMapping("/state")
public class ControllerState extends ControllerGeneric<State, DTORequestState, DTOResponseState> {

    private final ServiceState serviceState;

    public ControllerState(ServiceState serviceState) {
        super(serviceState);
        this.serviceState = serviceState;
    }
    protected Class<State> getEntityClass() {
        return State.class;
    }
}