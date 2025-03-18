package com.maps.persistence.payload.request;

import com.maps.exception.annotation.UniqueNameCountry;
import lombok.Getter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@UniqueNameCountry
public class DTORequestCountry extends Identifiable {

    private UUID id;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
}