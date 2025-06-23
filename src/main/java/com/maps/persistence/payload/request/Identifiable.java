package com.maps.persistence.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@Setter
public class Identifiable {

    private UUID id;
}