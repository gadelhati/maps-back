package com.maps.persistence;

import com.maps.persistence.model.InternationalChart;
import com.maps.persistence.payload.request.DTORequestInternationalChart;
import com.maps.persistence.payload.response.DTOResponseInternationalChart;
import org.mapstruct.Mapper;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Mapper(componentModel="spring")
public interface MapperInternationalChart extends MapperInterface<InternationalChart, DTORequestInternationalChart, DTOResponseInternationalChart> {
    DTOResponseInternationalChart toDTO(InternationalChart entity);
    InternationalChart toObject(DTORequestInternationalChart dto);
}