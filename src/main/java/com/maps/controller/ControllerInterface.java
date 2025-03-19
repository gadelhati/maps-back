package com.maps.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

public interface ControllerInterface<DTORequest, DTOResponse> {
    ResponseEntity<DTOResponse> create(@Valid @RequestBody DTORequest created);
    ResponseEntity<Page<DTOResponse>> retrieve(String value, Pageable pageable);
    ResponseEntity<Optional<DTOResponse>> retrieve(UUID id);
    ResponseEntity<DTOResponse> update(@Valid DTORequest updated);
    ResponseEntity<DTOResponse> delete(UUID id);
//    ResponseEntity<HttpStatus> deleteAll();
}