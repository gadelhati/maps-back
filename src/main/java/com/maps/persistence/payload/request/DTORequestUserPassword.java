package com.maps.persistence.payload.request;

import com.maps.exception.annotation.*;
import lombok.Getter;

import jakarta.validation.constraints.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Getter
public class DTORequestUserPassword extends DTORequestIdentifiable {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    @HasDigit
    @HasLetter
    @HasUpperCase
    @HasLowerCase
    @HasLength
    private String password;
}
