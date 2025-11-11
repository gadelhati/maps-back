package com.maps.persistence.payload.request;

import java.util.UUID;

import com.maps.exception.annotation.*;

import jakarta.validation.constraints.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

public record DTORequestUserPassword (

    UUID id,
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    @HasDigit
    @HasLetter
    @HasUpperCase
    @HasLowerCase
    @HasLength
    String password
) implements DTORequestIdentifiable {}
