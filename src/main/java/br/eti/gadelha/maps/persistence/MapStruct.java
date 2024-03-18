package br.eti.gadelha.maps.persistence;

import br.eti.gadelha.maps.persistence.model.*;
import br.eti.gadelha.maps.persistence.payload.request.*;
import br.eti.gadelha.maps.persistence.payload.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface MapStruct {

    MapStruct MAPPER = Mappers.getMapper(MapStruct.class);
    DTOResponseCountry toDTO(Country country);
    DTOResponseUser toDTO(User user);
    DTOResponseRole toDTO(Role role);
    DTOResponseLocation toDTO(Location location);
    DTOResponsePrivilege toDTO(Privilege privilege);
    DTOResponseCompositeUnit toDTO(CompositeUnit compositeUnit);
    DTOResponseState toDTO(State state);
    DTOResponseCity toDTO(City city);

    Country toObject(DTORequestCountry dtoRequestCountry);
    User toObject(DTORequestUser dtoRequestUser);
    Role toObject(DTORequestRole dtoRequestRole);
    Location toObject(DTORequestLocation dtoRequestLocation);
    Privilege toObject(DTORequestPrivilege dtoRequestPrivilege);
    CompositeUnit toObject(DTORequestCompositeUnit dtoRequestCompositeUnit);
    State toObject(DTORequestState dtoRequestState);
    City toObject(DTORequestCity dtoRequestCity);
}