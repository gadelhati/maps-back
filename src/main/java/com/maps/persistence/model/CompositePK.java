package com.maps.persistence.model;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@AllArgsConstructor
@NoArgsConstructor
public class CompositePK implements Serializable {

    private String name;
    private int number;
}