package com.maps.controller;

import com.maps.MapsApplication;
import com.maps.persistence.model.User;
import com.maps.persistence.payload.request.DTORequestUser;
import com.maps.persistence.payload.request.DTORequestUserPassword;
import com.maps.persistence.payload.request.DTORequestUserTOTP;
import com.maps.persistence.payload.response.DTOResponseUser;
import com.maps.service.ServiceUser;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@RestController
@RequestMapping("/user")
public class ControllerUser extends ControllerGeneric<User, DTORequestUser, DTOResponseUser> {

    private final ServiceUser serviceUser;
    private final static Logger LOGGER = LoggerFactory.getLogger(MapsApplication.class);

    public ControllerUser(ServiceUser serviceUser) {
        super(serviceUser);
        this.serviceUser = serviceUser;
    }
    protected Class<User> getEntityClass() {
        return User.class;
    }
    @PutMapping("/changePassword")
    @PreAuthorize("hasAnyRole('ADMIN') and hasAnyAuthority('user:update')")
    public ResponseEntity<DTOResponseUser> changePassword(@RequestBody @Valid DTORequestUserPassword updated){
        return new ResponseEntity<>(serviceUser.changePassword(updated), HttpStatus.OK);
    }
    @PutMapping("/totp")
    @PreAuthorize("hasAnyRole('ADMIN') and hasAnyAuthority('user:update')")
    public String resetTOTP(@Valid @RequestBody DTORequestUserTOTP userTOTP) {
        return serviceUser.resetTOTP(userTOTP.getUsername());
    }
}