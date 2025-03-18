package com.maps.persistence.payload.request;

import lombok.Data;

import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Data
public class Identifiable {
    private UUID id;
}