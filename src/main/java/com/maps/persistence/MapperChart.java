package com.maps.persistence;

import com.maps.persistence.model.Chart;
import com.maps.persistence.payload.request.DTORequestChart;
import com.maps.persistence.payload.response.DTOResponseChart;
import org.mapstruct.Mapper;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Mapper(componentModel="spring")
public interface MapperChart extends MapperInterface<Chart, DTORequestChart, DTOResponseChart> {
    DTOResponseChart toDTO(Chart entity);
    Chart toObject(DTORequestChart dto);
}