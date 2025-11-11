package com.maps.persistence.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

public record DTORequestEmail(

    @NotBlank(message = "{not.blank}") @Email(message = "{email}")
    String to,
    @NotBlank(message = "{not.blank}")
    String subject,
    @NotBlank(message = "{not.blank}")
    String text
) {}
