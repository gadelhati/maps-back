package com.maps.persistence.payload.request;

import java.util.UUID;

import com.maps.exception.annotation.UniqueNameResearcher;
import com.maps.persistence.model.Address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@UniqueNameResearcher(label = "name")
public record DTORequestResearcher (

    UUID id,
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    String name,
    String email,

    Address address
) implements DTORequestIdentifiable {}
