package com.maps.persistence;

import com.maps.persistence.model.Country;
import com.maps.persistence.payload.request.DTORequestCountry;
import com.maps.persistence.payload.response.DTOResponseCountry;
import org.mapstruct.Mapper;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Mapper(componentModel="spring")
public interface MapperCountry extends MapperInterface<Country, DTORequestCountry, DTOResponseCountry> {
    DTOResponseCountry toDTO(Country entity);
    Country toObject(DTORequestCountry dto);
}
