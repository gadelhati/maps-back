package com.maps.persistence.payload.response;

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
public class DTOResponseChartArea extends RepresentationModel<DTOResponseChartArea> {

    private UUID id;
    private String name;
}
