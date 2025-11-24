package com.maps.persistence.payload.response;

import com.maps.persistence.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Getter
@AllArgsConstructor
public class DTOResponseResearcher extends RepresentationModel<DTOResponseResearcher> {

    private UUID id;
    private String name;
    private String email;
    private Address address;
}
