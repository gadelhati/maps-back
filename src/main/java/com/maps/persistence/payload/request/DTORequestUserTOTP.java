package com.maps.persistence.payload.request;

import com.maps.exception.annotation.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@UniqueEmail(label = "email")
@UniqueNameUser(label = "username")
public class DTORequestUserTOTP {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String username;
    @NotNull(message = "{not.null}")
    private Integer totpKey;
}