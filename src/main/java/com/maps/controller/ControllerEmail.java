package com.maps.controller;

import com.maps.persistence.payload.request.DTORequestEmail;
import com.maps.service.ServiceEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@RestController
@RequestMapping("/email")
public class ControllerEmail {

    @Autowired
    private ServiceEmail emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody DTORequestEmail dtoRequestEmail) {
        emailService.sendSimpleMessage(
                dtoRequestEmail.getTo(),
                dtoRequestEmail.getSubject(),
                dtoRequestEmail.getText()
        );
        return ResponseEntity.ok("Email enviado com sucesso!");
    }
}