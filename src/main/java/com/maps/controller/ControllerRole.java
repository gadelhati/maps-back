package com.maps.controller;

import com.maps.persistence.model.Role;
import com.maps.persistence.payload.request.DTORequestRole;
import com.maps.persistence.payload.response.DTOResponseRole;
import com.maps.service.ServiceRole;
import org.springframework.web.bind.annotation.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@RestController
@RequestMapping("/role")
public class ControllerRole extends ControllerGeneric<Role, DTORequestRole, DTOResponseRole> {

    private final ServiceRole serviceRole;

    public ControllerRole(ServiceRole serviceRole) {
        super(serviceRole);
        this.serviceRole = serviceRole;
    }
    protected Class<Role> getEntityClass() {
        return Role.class;
    }
}