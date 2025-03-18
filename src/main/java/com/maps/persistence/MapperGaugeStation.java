package com.maps.persistence;

import com.maps.persistence.model.GaugeStation;
import com.maps.persistence.payload.request.DTORequestGaugeStation;
import com.maps.persistence.payload.response.DTOResponseGaugeStation;
import org.mapstruct.Mapper;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Mapper(componentModel="spring")
public interface MapperGaugeStation extends MapperInterface<GaugeStation, DTORequestGaugeStation, DTOResponseGaugeStation> {
    DTOResponseGaugeStation toDTO(GaugeStation entity);
    GaugeStation toObject(DTORequestGaugeStation dto);
}