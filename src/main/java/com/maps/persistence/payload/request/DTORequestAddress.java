package com.maps.persistence.payload.request;

import java.util.UUID;

import com.maps.persistence.model.City;

/**
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 **/

public record DTORequestAddress (

    UUID id,
    String street,
    String number,
    String cep,
    String complement,
    String neighbourhood,

    City city
) implements DTORequestIdentifiable {}
