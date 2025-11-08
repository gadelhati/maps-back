package com.maps.persistence.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Getter
@Setter
public class DTORequestIdentifiable {

    private UUID id;
}
