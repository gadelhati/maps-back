package com.maps.persistence.payload.request;

import com.maps.persistence.model.remodel.City;
import lombok.Getter;

@Getter
public class DTORequestAddress extends Identifiable {

    private String street;
    private String number;
    private String cep;
    private String complement;
    private String neighbourhood;

    private City city;
}