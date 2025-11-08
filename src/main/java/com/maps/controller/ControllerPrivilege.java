package com.maps.controller;

import com.maps.persistence.model.Privilege;
import com.maps.persistence.payload.request.DTORequestPrivilege;
import com.maps.persistence.payload.response.DTOResponsePrivilege;
import com.maps.service.ServicePrivilege;
import org.springframework.web.bind.annotation.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@RestController
@RequestMapping("/privilege")
public class ControllerPrivilege extends ControllerGeneric<Privilege, DTORequestPrivilege, DTOResponsePrivilege> {

    private final ServicePrivilege servicePrivilege;

    public ControllerPrivilege(ServicePrivilege servicePrivilege) {
        super(servicePrivilege);
        this.servicePrivilege = servicePrivilege;
    }
    protected Class<Privilege> getEntityClass() {
        return Privilege.class;
    }
}
