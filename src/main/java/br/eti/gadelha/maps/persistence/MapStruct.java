package br.eti.gadelha.maps.persistence;

import br.eti.gadelha.maps.persistence.model.*;
import br.eti.gadelha.maps.persistence.payload.request.*;
import br.eti.gadelha.maps.persistence.payload.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface MapStruct {

    MapStruct MAPPER = Mappers.getMapper(MapStruct.class);
    DTOResponseChart toDTO(Chart chart);
    DTOResponseChartArea toDTO(ChartArea chartArea);
    DTOResponseCity toDTO(City city);
    DTOResponseCompositeUnit toDTO(CompositeUnit compositeUnit);
    DTOResponseCountry toDTO(Country country);
    DTOResponseGaugeStation toDTO(GaugeStation gaugeStation);
    DTOResponseInternationalChart toDTO(InternationalChart internationalChart);
    DTOResponseMaritimeArea toDTO(MaritimeArea maritimeArea);
    DTOResponsePrivilege toDTO(Privilege privilege);
    DTOResponseState toDTO(State state);
    DTOResponseRole toDTO(Role role);
    DTOResponseUser toDTO(User user);

    Chart toObject(DTORequestChart dtoRequestChart);
    ChartArea toObject(DTORequestChartArea dtoRequestChartArea);
    City toObject(DTORequestCity dtoRequestCity);
    CompositeUnit toObject(DTORequestCompositeUnit dtoRequestCompositeUnit);
    Country toObject(DTORequestCountry dtoRequestCountry);
    GaugeStation toObject(DTORequestGaugeStation dtoRequestGaugeStation);
    InternationalChart toObject(DTORequestInternationalChart dtoRequestInternationalChart);
    MaritimeArea toObject(DTORequestMaritimeArea dtoRequestMaritimeArea);
    Privilege toObject(DTORequestPrivilege dtoRequestPrivilege);
    State toObject(DTORequestState dtoRequestState);
    Role toObject(DTORequestRole dtoRequestRole);
    User toObject(DTORequestUser dtoRequestUser);
}