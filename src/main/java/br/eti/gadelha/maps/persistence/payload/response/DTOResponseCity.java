package br.eti.gadelha.maps.persistence.payload.response;

import br.eti.gadelha.maps.persistence.model.State;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter @AllArgsConstructor
public class DTOResponseCity {

    private UUID id;
    private String code;
    private String name;
    private State state;
}