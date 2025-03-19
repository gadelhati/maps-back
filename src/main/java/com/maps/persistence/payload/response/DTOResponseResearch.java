package com.maps.persistence.payload.response;

import com.maps.persistence.model.Commission;
import com.maps.persistence.model.Researcher;
import com.maps.persistence.model.bndo.Module;
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
    private LocalDateTime start;
    private LocalDateTime finish;
    private Researcher researcher;
    private Commission commission;
    private Module module;
}