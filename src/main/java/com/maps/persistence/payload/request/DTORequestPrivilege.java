package com.maps.persistence.payload.request;

import java.util.UUID;

import com.maps.exception.annotation.UniqueNamePrivilege;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@UniqueNamePrivilege(label = "name")
public record DTORequestPrivilege (

    UUID id,
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    String name
) implements DTORequestIdentifiable {}
