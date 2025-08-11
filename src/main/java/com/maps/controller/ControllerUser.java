package com.maps.controller;

import com.maps.MapsApplication;
import com.maps.exception.ApiError;
import com.maps.persistence.model.User;
import com.maps.persistence.payload.request.DTORequestUser;
import com.maps.persistence.payload.request.DTORequestUserAuth;
import com.maps.persistence.payload.request.DTORequestUserPassword;
import com.maps.persistence.payload.response.DTOResponseUser;
import com.maps.service.ServiceUser;
import com.maps.service.ServiceUserAuth;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@RestController
@RequestMapping("/user")
public class ControllerUser extends ControllerGeneric<User, DTORequestUser, DTOResponseUser> {

    private final ServiceUser serviceUser;
    private final ServiceUserAuth serviceUserAuth;
    private final static Logger LOGGER = LoggerFactory.getLogger(MapsApplication.class);

    public ControllerUser(ServiceUser serviceUser, ServiceUserAuth serviceUserAuth) {
        super(serviceUser);
        this.serviceUser = serviceUser;
        this.serviceUserAuth = serviceUserAuth;
    }
    protected Class<User> getEntityClass() {
        return User.class;
    }
    @PostMapping("/signup")
    public ResponseEntity<ApiError> signUp(@RequestBody @Valid DTORequestUser value) {
//        serviceUserAuth.register(value.getUsername(), value.getEmail());
        return ResponseEntity.accepted().body(new ApiError(HttpStatus.CREATED, "", ""));
    }
    @PutMapping("/changePassword")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    public ResponseEntity<DTOResponseUser> changePassword(@RequestBody @Valid DTORequestUserPassword updated){
        return ResponseEntity.accepted().body(serviceUser.changePassword(updated));
    }
    @PutMapping("/resetPassword")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER', 'VIEWER')")
    public ResponseEntity<DTOResponseUser> resetPassword(@RequestBody DTORequestUserAuth updated) {
        return ResponseEntity.accepted().body(serviceUser.resetPassword(updated.getUsername()));
    }
    @PutMapping("/resetTotp")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER', 'VIEWER')")
    public ResponseEntity<DTOResponseUser> resetSecret(@RequestBody DTORequestUserAuth updated) {
        return ResponseEntity.accepted().body(serviceUser.resetSecret(updated.getUsername()));
    }
}