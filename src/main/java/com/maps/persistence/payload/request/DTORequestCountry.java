package com.maps.persistence.payload.request;

import java.util.UUID;

import com.maps.exception.annotation.UniqueNameCountry;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@UniqueNameCountry(label = "name")
public record DTORequestCountry (

    UUID id,
    Integer code,
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    String name
) implements DTORequestIdentifiable {}
