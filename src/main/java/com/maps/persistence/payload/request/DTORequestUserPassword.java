package com.maps.persistence.payload.request;

import com.maps.exception.annotation.*;
import lombok.Getter;

import jakarta.validation.constraints.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@UniqueEmail
@UniqueNameUser(label = "username")
public class DTORequestUserPassword extends Identifiable {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    @HasDigit
    @HasLetter
    @HasUpperCase
    @HasLowerCase
    @HasLength
    private String password;
}