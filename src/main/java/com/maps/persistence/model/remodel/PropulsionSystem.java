package com.maps.persistence.model.remodel;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@AllArgsConstructor
public enum PropulsionSystem {
    SAIL("Sail"),
    OARS("Oars"),
    DIESEL_ENGINE("Diesel Engine"),
    GASOLINE_ENGINE("Gasoline Engine"),
    ELECTRIC_ENGINE("Electric Engine"),
    HYBRID("Hybrid"),
    NUCLEAR("Nuclear Propulsion"),
    NO_PROPULSION("No Propulsion");

    private final String description;
}