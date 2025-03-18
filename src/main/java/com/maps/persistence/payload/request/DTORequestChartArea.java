package com.maps.persistence.payload.request;

import com.maps.exception.annotation.UniqueNameChartArea;
import com.maps.persistence.payload.response.DTOResponseChartArea;
import lombok.Getter;

import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@UniqueNameChartArea
public class DTORequestChartArea extends Identifiable {

    private UUID id;
    private String name;
}