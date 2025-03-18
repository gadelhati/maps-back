package com.maps.persistence;

import com.maps.persistence.model.User;
import com.maps.persistence.payload.request.DTORequestUser;
import com.maps.persistence.payload.response.DTOResponseUser;
import org.mapstruct.Mapper;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Mapper(componentModel="spring")
public interface MapperUser extends MapperInterface<User, DTORequestUser, DTOResponseUser> {
    DTOResponseUser toDTO(User entity);
    User toObject(DTORequestUser dto);
}