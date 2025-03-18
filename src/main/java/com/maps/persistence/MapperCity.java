package com.maps.persistence;

import com.maps.persistence.model.City;
import com.maps.persistence.payload.request.DTORequestCity;
import com.maps.persistence.payload.response.DTOResponseCity;
import org.mapstruct.Mapper;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Mapper(componentModel="spring")
public interface MapperCity extends MapperInterface<City, DTORequestCity, DTOResponseCity> {
    DTOResponseCity toDTO(City entity);
    City toObject(DTORequestCity dto);
}