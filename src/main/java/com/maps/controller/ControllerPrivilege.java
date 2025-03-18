package com.maps.controller;

import com.maps.persistence.model.Privilege;
import com.maps.persistence.payload.request.DTORequestPrivilege;
import com.maps.persistence.payload.response.DTOResponsePrivilege;
import com.maps.service.ServicePrivilege;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
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