package com.maps.persistence;

import com.maps.persistence.model.remodel.State;
import com.maps.persistence.payload.request.DTORequestState;
import com.maps.persistence.payload.response.DTOResponseState;
import org.mapstruct.Mapper;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Mapper(componentModel="spring")
public interface MapperState extends MapperInterface<State, DTORequestState, DTOResponseState> {
    DTOResponseState toDTO(State entity);
    State toObject(DTORequestState dto);
}