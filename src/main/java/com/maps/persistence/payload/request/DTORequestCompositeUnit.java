package com.maps.persistence.payload.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

public record DTORequestCompositeUnit (

    UUID id,
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    String name,
    @NotNull(message = "{not.null}") @Min(1)
    int number,
    String value,
    @NotNull(message = "{not.null}")
    Date date
) implements DTORequestIdentifiable {}
