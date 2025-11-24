package com.maps.persistence.payload.request;

import java.util.UUID;

import com.maps.persistence.model.State;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

public record DTORequestCity (

    UUID id,
    Integer code,
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    String name,
    State state
) implements DTORequestIdentifiable {}
