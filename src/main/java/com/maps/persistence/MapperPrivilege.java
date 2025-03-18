package com.maps.persistence;

import com.maps.persistence.model.Privilege;
import com.maps.persistence.payload.request.DTORequestPrivilege;
import com.maps.persistence.payload.response.DTOResponsePrivilege;
import org.mapstruct.Mapper;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Mapper(componentModel="spring")
public interface MapperPrivilege extends MapperInterface<Privilege, DTORequestPrivilege, DTOResponsePrivilege> {
    DTOResponsePrivilege toDTO(Privilege entity);
    Privilege toObject(DTORequestPrivilege dto);
}