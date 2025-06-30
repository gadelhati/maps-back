package com.maps.persistence;

import com.maps.persistence.model.*;
import com.maps.persistence.model.remodel.City;
import com.maps.persistence.model.remodel.Country;
import com.maps.persistence.model.remodel.State;
import com.maps.persistence.payload.request.*;
import com.maps.persistence.payload.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Mapper(componentModel="spring")
public interface MapStruct {

    MapStruct MAPPER = Mappers.getMapper(MapStruct.class);
    DTOResponseUser toDTO(User user);
    DTOResponseToken toDTO(Token token);
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

    User toObject(DTORequestUser dtoRequestUser);
    User toObject(DTORequestUserAuth dtoRequestUserAuth);
    Token toObject(DTORequestToken dtoRequestToken);
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
}