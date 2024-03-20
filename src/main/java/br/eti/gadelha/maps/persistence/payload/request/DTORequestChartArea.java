package br.eti.gadelha.maps.persistence.payload.request;

import br.eti.gadelha.maps.exception.annotation.UniqueNameCountry;
import lombok.Getter;

import java.util.UUID;

@Getter @UniqueNameCountry
public class DTORequestChartArea {

    private UUID id;
    private String name;
}