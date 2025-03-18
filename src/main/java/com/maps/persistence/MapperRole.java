package com.maps.persistence;

import com.maps.persistence.model.Role;
import com.maps.persistence.payload.request.DTORequestRole;
import com.maps.persistence.payload.response.DTOResponseRole;
import org.mapstruct.Mapper;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Mapper(componentModel="spring")
public interface MapperRole extends MapperInterface<Role, DTORequestRole, DTOResponseRole> {
    DTOResponseRole toDTO(Role entity);
    Role toObject(DTORequestRole dto);
}