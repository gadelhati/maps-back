package com.maps.persistence.payload.request;

import com.maps.exception.annotation.UniqueNameChartArea;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Getter
@UniqueNameChartArea(label = "name")
public class DTORequestChartArea extends DTORequestIdentifiable {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
}
