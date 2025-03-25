package com.maps.persistence.payload.response;

import com.maps.persistence.model.Privilege;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@AllArgsConstructor
public class DTOResponseRole extends RepresentationModel<DTOResponseRole> {

    private UUID id;
    private String name;
    private Set<Privilege> privilege = new HashSet<>();
}