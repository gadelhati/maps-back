package com.maps.persistence.payload.request;

import com.maps.exception.annotation.UniqueNameRole;
import com.maps.persistence.model.Privilege;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@UniqueNameRole(label = "name")
public class DTORequestRole extends Identifiable {

    private UUID id;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
    private Set<Privilege> privileges = new HashSet<>();
}