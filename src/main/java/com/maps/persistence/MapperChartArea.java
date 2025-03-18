package com.maps.persistence;

import com.maps.persistence.model.ChartArea;
import com.maps.persistence.payload.request.DTORequestChartArea;
import com.maps.persistence.payload.response.DTOResponseChartArea;
import org.mapstruct.Mapper;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Mapper(componentModel="spring")
public interface MapperChartArea extends MapperInterface<ChartArea, DTORequestChartArea, DTOResponseChartArea> {
    DTOResponseChartArea toDTO(ChartArea entity);
    ChartArea toObject(DTORequestChartArea dto);
}