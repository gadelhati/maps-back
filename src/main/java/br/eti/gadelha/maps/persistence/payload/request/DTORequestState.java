package br.eti.gadelha.maps.persistence.payload.request;

import br.eti.gadelha.maps.persistence.model.Country;
import lombok.Getter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Getter
public class DTORequestState {

    private UUID id;
    private String code;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
    private Country country;
}