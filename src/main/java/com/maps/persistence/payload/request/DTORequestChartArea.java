package com.maps.persistence.payload.request;

import com.maps.exception.annotation.UniqueNameChartArea;
import lombok.Getter;


/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@UniqueNameChartArea
public class DTORequestChartArea extends Identifiable {

    private String name;
}