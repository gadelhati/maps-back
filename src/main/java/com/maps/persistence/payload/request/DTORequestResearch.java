package com.maps.persistence.payload.request;

import com.maps.persistence.model.Commission;
import com.maps.persistence.model.Researcher;
import com.maps.persistence.model.bndo.Module;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
public class DTORequestResearch extends Identifiable {

    private LocalDateTime start;
    private LocalDateTime finish;
    private Researcher researcher;
    private Commission commission;
    private Module module;
}