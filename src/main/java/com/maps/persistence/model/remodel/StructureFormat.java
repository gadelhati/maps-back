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
public enum StructureFormat {
    SPHERICAL("Spherical"),
    PILLAR("Pillar"),
    CIGAR("Cigar"),
    CONICAL("Conical"),
    CYLINDRICAL("Cylindrical"),
    FRUSTUM_CONICAL("Frustum Conical"),
    QUADRANGULAR("Quadrangular");

    private final String name;
}