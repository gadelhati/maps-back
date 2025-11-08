package com.maps.persistence;

import com.maps.persistence.model.remodel.Researcher;
import com.maps.persistence.payload.request.DTORequestResearcher;
import com.maps.persistence.payload.response.DTOResponseResearcher;
import org.mapstruct.Mapper;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Mapper(componentModel="spring")
public interface MapperResearcher extends MapperInterface<Researcher, DTORequestResearcher, DTOResponseResearcher> {
    DTOResponseResearcher toDTO(Researcher entity);
    Researcher toObject(DTORequestResearcher dto);
}
