package br.eti.gadelha.maps.persistence.payload.request;

import br.eti.gadelha.maps.exception.annotation.UniqueNameChartArea;
import lombok.Getter;

import java.util.UUID;

@Getter @UniqueNameChartArea
public class DTORequestChartArea {

    private UUID id;
    private String name;
}