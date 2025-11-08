package com.maps.persistence.payload.request;

import com.maps.persistence.model.remodel.City;
import lombok.Getter;

/**
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 **/

@Getter
public class DTORequestAddress extends DTORequestIdentifiable {

    private String street;
    private String number;
    private String cep;
    private String complement;
    private String neighbourhood;

    private City city;
}
