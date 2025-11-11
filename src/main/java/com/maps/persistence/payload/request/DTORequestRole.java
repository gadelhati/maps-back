package com.maps.persistence.payload.request;

import com.maps.exception.annotation.UniqueNameRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@UniqueNameRole(label = "name")
public record DTORequestRole (

    UUID id,
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    String name
) implements DTORequestIdentifiable {}
