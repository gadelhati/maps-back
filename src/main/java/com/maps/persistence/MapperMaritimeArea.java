package com.maps.persistence;

import com.maps.persistence.model.MaritimeArea;
import com.maps.persistence.payload.request.DTORequestMaritimeArea;
import com.maps.persistence.payload.response.DTOResponseMaritimeArea;
import org.mapstruct.Mapper;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Mapper(componentModel="spring")
public interface MapperMaritimeArea extends MapperInterface<MaritimeArea, DTORequestMaritimeArea, DTOResponseMaritimeArea> {
    DTOResponseMaritimeArea toDTO(MaritimeArea entity);
    MaritimeArea toObject(DTORequestMaritimeArea dto);
}