package com.maps.persistence.payload.request;

import com.maps.exception.annotation.UniqueNameRole;
import com.maps.persistence.model.Privilege;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@UniqueNameRole(label = "name")
public class DTORequestRole extends Identifiable {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
    private Set<Privilege> privilege = new HashSet<>();
}