package br.eti.gadelha.maps.persistence.payload.request;

import br.eti.gadelha.maps.exception.annotation.*;
import br.eti.gadelha.maps.persistence.model.Role;
import lombok.Getter;

import jakarta.validation.constraints.*;
import java.util.Collection;
import java.util.UUID;

@Getter @UniqueUsername @UniqueEmail
public class DTORequestUserPassword {

    private UUID id;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    @HasDigit
    @HasLetter
    @HasUpperCase
    @HasLowerCase
    @HasLength
    private String password;
}