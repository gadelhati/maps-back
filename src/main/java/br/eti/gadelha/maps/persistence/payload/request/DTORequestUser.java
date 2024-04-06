package br.eti.gadelha.maps.persistence.payload.request;

import br.eti.gadelha.maps.exception.annotation.*;
import br.eti.gadelha.maps.persistence.model.Role;
import lombok.Getter;

import jakarta.validation.constraints.*;
import java.util.Collection;
import java.util.UUID;

@Getter @UniqueUsername @UniqueEmail
public class DTORequestUser {

    private UUID id;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}") @HasLength
    private String username;
    @NotBlank(message = "{not.blank}") @Size(max = 50) @Email
    private String email;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    @HasDigit
    @HasLetter
    @HasUpperCase
    @HasLowerCase
    @HasLength
    private String password;
    @NotNull(message = "{user.active.not.null}")
    private boolean active;
    private Collection<Role> role;
}