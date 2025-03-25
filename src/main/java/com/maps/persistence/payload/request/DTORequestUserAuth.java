package com.maps.persistence.payload.request;

import com.maps.exception.annotation.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DTORequestUserAuth {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String username;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    @HasDigit
    @HasLetter
    @HasUpperCase
    @HasLowerCase
    @HasLength
    private String password;
    @NotNull(message = "{not.null}")
    private Integer totpKey;
    private String captchaToken;
}