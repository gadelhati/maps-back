package com.maps.persistence.payload.request;

import com.maps.exception.annotation.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

public record DTORequestUserAuth(
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    String username,
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    @HasDigit
    @HasLetter
    @HasUpperCase
    @HasLowerCase
    @HasLength
    String password,
    @NotNull(message = "{not.null}")
    Integer totpKey,
    String captchaToken
) {}
