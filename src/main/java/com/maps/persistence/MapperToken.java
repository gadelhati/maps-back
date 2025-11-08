package com.maps.persistence;

import com.maps.persistence.model.Token;
import com.maps.persistence.payload.request.DTORequestToken;
import com.maps.persistence.payload.response.DTOResponseToken;
import org.mapstruct.Mapper;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Mapper(componentModel="spring")
public interface MapperToken extends MapperInterface<Token, DTORequestToken, DTOResponseToken> {
    DTOResponseToken toDTO(Token entity);
    Token toObject(DTORequestToken dto);
}
