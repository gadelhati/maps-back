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
public enum NavigationArea {
    INTERIOR("Navegação Interior", "Rios, lagos e canais"),
    COSTEIRA("Navegação Costeira", "Até 20 milhas náuticas da costa"),
    CABOTAGEM("Cabotagem", "Entre portos nacionais"),
    LONGO_CURSO("Longo Curso", "Navegação internacional"),
    APOIO_PORTUARIO("Apoio Portuário", "Operações portuárias"),
    APOIO_MARITIMO("Apoio Marítimo", "Apoio a plataformas e embarcações");

    private final String nome;
    private final String descricao;
}