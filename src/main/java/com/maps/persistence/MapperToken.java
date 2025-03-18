package com.maps.persistence;

import com.maps.persistence.model.Token;
import com.maps.persistence.payload.request.DTORequestToken;
import com.maps.persistence.payload.response.DTOResponseToken;
import org.mapstruct.Mapper;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Mapper(componentModel="spring")
public interface MapperToken extends MapperInterface<Token, DTORequestToken, DTOResponseToken> {
    DTOResponseToken toDTO(Token entity);
    Token toObject(DTORequestToken dto);
}