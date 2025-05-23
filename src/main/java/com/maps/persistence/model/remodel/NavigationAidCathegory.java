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
public enum NavigationAidCathegory {
    FAROL("Farol", "F"),
    BOIA("Boia", "B"),
    BALIZA("Baliza", "Bz"),
    SINAL_CARDINAL_NORTE("Sinal Cardinal Norte", "SCN"),
    SINAL_CARDINAL_SUL("Sinal Cardinal Sul", "SCS"),
    SINAL_CARDINAL_LESTE("Sinal Cardinal Leste", "SCE"),
    SINAL_CARDINAL_OESTE("Sinal Cardinal Oeste", "SCO"),
    MARCA_LATERAL_BOMBORDO("Marca Lateral Bombordo", "MLB"),
    MARCA_LATERAL_BORESTE("Marca Lateral Boreste", "MLBe"),
    MARCA_PERIGO_ISOLADO("Marca de Perigo Isolado", "MPI"),
    MARCA_AGUA_SEGURA("Marca de Água Segura", "MAS");

    private final String nome;
    private final String abreviacao;
}