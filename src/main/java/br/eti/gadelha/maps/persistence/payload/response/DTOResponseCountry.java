package br.eti.gadelha.maps.persistence.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter @AllArgsConstructor
public class DTOResponseCountry {

    private UUID id;
    private String name;
}