package com.maps.persistence.payload.request;

import com.maps.persistence.model.Role;
import com.maps.exception.annotation.*;

import jakarta.validation.constraints.*;
import java.util.Set;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@UniqueEmail(label = "email")
@UniqueNameUser(label = "username")
public record DTORequestUser (

    UUID id,
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}") @HasLength
    String username,
    @NotBlank(message = "{not.blank}") @Size(max = 50) @Email
    String email,

    Set<Role> role
) implements DTORequestIdentifiable {}
