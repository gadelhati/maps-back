package com.maps.persistence.payload.request;

import com.maps.persistence.model.Role;
import com.maps.exception.annotation.*;
import lombok.Getter;

import jakarta.validation.constraints.*;
import java.util.Set;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@UniqueEmail(label = "email")
@UniqueNameUser(label = "username")
public class DTORequestUser extends Identifiable {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}") @HasLength
    private String username;
    @NotBlank(message = "{not.blank}") @Size(max = 50) @Email
    private String email;
    private Set<Role> role;

    public DTORequestUser(String username, String email) {
        this.username = username;
        this.email = email;
    }
}