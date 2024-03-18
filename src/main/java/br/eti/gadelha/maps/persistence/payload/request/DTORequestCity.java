package br.eti.gadelha.maps.persistence.payload.request;

import br.eti.gadelha.maps.persistence.model.State;
import lombok.Getter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
public class DTORequestCity {

    private long id;
    @NotNull(message = "{name.not.null}") @NotBlank(message = "{name.not.blank}")
    private String name;
    private State state;
}