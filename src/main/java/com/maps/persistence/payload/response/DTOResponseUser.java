package com.maps.persistence.payload.response;

import com.maps.persistence.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@AllArgsConstructor
public class DTOResponseUser extends RepresentationModel<DTOResponseUser> {

    private UUID id;
    private String username;
    private String email;
    private Integer attempt;
    private Boolean active;
    private Set<Role> role;
}