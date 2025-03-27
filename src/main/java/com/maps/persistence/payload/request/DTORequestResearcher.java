package com.maps.persistence.payload.request;

import com.maps.exception.annotation.UniqueNameResearcher;
import lombok.Getter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@UniqueNameResearcher(label = "name")
public class DTORequestResearcher extends Identifiable {

    private int code;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
    private String email;
    private String address;
}