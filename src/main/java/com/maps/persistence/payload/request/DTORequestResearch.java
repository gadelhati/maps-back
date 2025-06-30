package com.maps.persistence.payload.request;

import com.maps.persistence.model.remodel.Cruise;
import com.maps.persistence.model.remodel.Module;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
public class DTORequestResearch extends Identifiable {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
    private String description;
    private LocalDateTime start;
    private LocalDateTime finish;

    private Cruise cruise;
    private Module module;
}