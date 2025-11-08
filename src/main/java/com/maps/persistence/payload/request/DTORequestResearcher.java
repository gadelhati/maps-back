package com.maps.persistence.payload.request;

import com.maps.exception.annotation.UniqueNameResearcher;
import com.maps.persistence.model.remodel.Address;
import lombok.Getter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Getter
@UniqueNameResearcher(label = "name")
public class DTORequestResearcher extends DTORequestIdentifiable {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
    private String email;
    private Address address;
}
