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
public enum VesselType {
    WARSHIP("Warship"),
    MERCHANT("Merchant Vessel"),
    RECREATIONAL("Recreational Vessel"),
    SPECIAL_SERVICES("Special Services"),
    FISHING("Fishing Vessel"),
    RESEARCH("Research Vessel");

    private final String description;
}