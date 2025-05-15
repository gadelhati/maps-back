package com.maps.persistence.payload.response;

import com.maps.persistence.model.remodel.City;
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
public class DTOResponseAddress extends RepresentationModel<DTOResponseAddress> {

    private UUID id;
    private String street;
    private String number;
    private String cep;
    private String complement;
    private String neighbourhood;

    private City city;
}