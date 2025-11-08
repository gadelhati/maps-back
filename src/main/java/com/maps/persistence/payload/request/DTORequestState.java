package com.maps.persistence.payload.request;

import com.maps.persistence.model.remodel.Country;
import lombok.Getter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Getter
public class DTORequestState extends DTORequestIdentifiable {

    private Integer code;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
    private Country country;
}
