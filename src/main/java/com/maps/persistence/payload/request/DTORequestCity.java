package com.maps.persistence.payload.request;

import com.maps.persistence.model.State;
import com.maps.persistence.payload.response.DTOResponseCity;
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
public class DTORequestCity extends Identifiable {

    private UUID id;
    private String code;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
    private State state;
}