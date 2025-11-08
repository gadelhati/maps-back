package com.maps.controller;

import com.maps.persistence.payload.request.DTORequestUserAuth;
import com.maps.persistence.payload.request.DTORequestToken;
import com.maps.persistence.payload.response.DTOResponseToken;
import com.maps.service.ServiceAuth;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class ControllerAuth {

    private final ServiceAuth serviceAuth;

    @PostMapping("/login")
    public ResponseEntity<DTOResponseToken> login(@RequestBody @Valid DTORequestUserAuth value){
        return ResponseEntity.ok().body(serviceAuth.login(value));
    }
    @PostMapping("/refresh")
    public ResponseEntity<DTOResponseToken> refresh(@RequestBody @Valid DTORequestToken value){
        return ResponseEntity.accepted().body(serviceAuth.refresh(value));
    }
    @DeleteMapping("/logout/{refreshToken}")
    public ResponseEntity<DTOResponseToken> logout(@PathVariable("refreshToken") UUID refreshToken) {
        return ResponseEntity.accepted().body(serviceAuth.logout(refreshToken));
    }
}
