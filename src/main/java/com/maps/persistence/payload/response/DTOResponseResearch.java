package com.maps.persistence.payload.response;

import com.maps.persistence.model.remodel.Commission;
import com.maps.persistence.model.remodel.Module;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@AllArgsConstructor
public class DTOResponseResearch extends RepresentationModel<DTOResponseResearch> {

    private UUID id;
    private String name;
    private String description;
    private LocalDateTime start;
    private LocalDateTime finish;

    private Commission commission;
    private Module module;
}