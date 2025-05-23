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
public enum HullMaterial {
    MADEIRA("Madeira"),
    ACO("Aço"),
    FERRO("Ferro"),
    ALUMINIO("Alumínio"),
    FIBRA_VIDRO("Fibra de Vidro"),
    CONCRETO_ARMADO("Concreto Armado"),
    COMPOSITO("Material Compósito");

    private final String descricao;
}