package com.maps.persistence.payload.response;

import com.maps.persistence.model.Country;
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
public class DTOResponseState extends RepresentationModel<DTOResponseState> {

    private UUID id;
    private Integer code;
    private String name;
    private Country country;
}