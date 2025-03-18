package com.maps.persistence.payload.request;

import com.maps.exception.annotation.UniqueNamePrivilege;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@UniqueNamePrivilege(label = "name")
public class DTORequestPrivilege extends Identifiable {

    private UUID id;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
}