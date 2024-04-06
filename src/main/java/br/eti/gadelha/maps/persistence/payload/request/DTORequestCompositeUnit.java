package br.eti.gadelha.maps.persistence.payload.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Date;

@Getter
public class DTORequestCompositeUnit {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
    @NotNull(message = "{not.null}") @Min(1)
    private int number;
    private String value;
    @NotNull(message = "{not.null}")
    private Date date;
}