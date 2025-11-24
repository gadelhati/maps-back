package com.maps.persistence.payload.response;

import com.maps.persistence.model.State;
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
public class DTOResponseCity extends RepresentationModel<DTOResponseCity> {

    private UUID id;
    private Integer code;
    private String name;
    private State state;
}
