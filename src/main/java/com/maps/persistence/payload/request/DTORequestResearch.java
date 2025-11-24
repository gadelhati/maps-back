package com.maps.persistence.payload.request;

import com.maps.persistence.model.Cruise;
import com.maps.persistence.model.Module;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

public record DTORequestResearch (

    UUID id,
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    String name,
    String description,
    LocalDateTime start,
    LocalDateTime finish,

    Cruise cruise,
    Module module
) implements DTORequestIdentifiable {}
