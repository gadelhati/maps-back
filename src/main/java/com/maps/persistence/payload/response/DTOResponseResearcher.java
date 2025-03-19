package com.maps.persistence.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@AllArgsConstructor
public class DTOResponseResearcher extends RepresentationModel<DTOResponseResearcher> {

    private UUID id;
    private int code;
    private String name;
    private String email;
    private String address;
}