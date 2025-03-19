package com.maps.persistence;

import com.maps.persistence.model.Research;
import com.maps.persistence.payload.request.DTORequestResearch;
import com.maps.persistence.payload.response.DTOResponseResearch;
import org.mapstruct.Mapper;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Mapper(componentModel="spring")
public interface MapperResearch extends MapperInterface<Research, DTORequestResearch, DTOResponseResearch> {
    DTOResponseResearch toDTO(Research entity);
    Research toObject(DTORequestResearch dto);
}