package com.maps.persistence.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Data
public class DTORequestEmail {

    @NotBlank(message = "{not.blank}") @Email(message = "{email}")
    private String to;
    @NotBlank(message = "{not.blank}")
    private String subject;
    @NotBlank(message = "{not.blank}")
    private String text;
}