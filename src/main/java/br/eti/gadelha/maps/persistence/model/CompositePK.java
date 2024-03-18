package br.eti.gadelha.maps.persistence.model;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor @NoArgsConstructor
public class CompositePK implements Serializable {

    private String name;
    private int number;
}