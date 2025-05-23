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
public enum LightColor {
    BRANCA("Branca", "B"),
    VERMELHA("Vermelha", "V"),
    VERDE("Verde", "Vd"),
    AMARELA("Amarela", "A"),
    AZUL("Azul", "Az");

    private final String nome;
    private final String abreviacao;
}
