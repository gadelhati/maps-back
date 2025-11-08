package com.maps.persistence.model;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@AllArgsConstructor
@NoArgsConstructor
public class CompositePK implements Serializable {

    private String name;
    private int number;
}
