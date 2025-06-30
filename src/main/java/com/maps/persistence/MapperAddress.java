package com.maps.persistence;

import com.maps.persistence.model.remodel.Address;
import com.maps.persistence.payload.request.DTORequestAddress;
import com.maps.persistence.payload.response.DTOResponseAddress;
import org.mapstruct.Mapper;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Mapper(componentModel="spring")
public interface MapperAddress extends MapperInterface<Address, DTORequestAddress, DTOResponseAddress> {
    DTOResponseAddress toDTO(Address entity);
    Address toObject(DTORequestAddress dto);
}