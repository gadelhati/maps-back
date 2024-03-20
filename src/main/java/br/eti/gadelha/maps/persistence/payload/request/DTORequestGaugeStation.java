package br.eti.gadelha.maps.persistence.payload.request;

import br.eti.gadelha.maps.exception.annotation.UniqueNameCountry;
import br.eti.gadelha.maps.persistence.model.ChartArea;
import br.eti.gadelha.maps.persistence.model.State;
import jakarta.persistence.*;
import lombok.Getter;
import org.locationtech.jts.geom.*;

import java.util.UUID;

@Getter @UniqueNameCountry
public class DTORequestGaugeStation {

    private UUID id;
    private String number;
    private String title;
    private Point point;

    private State state;
    private ChartArea chartArea;
}