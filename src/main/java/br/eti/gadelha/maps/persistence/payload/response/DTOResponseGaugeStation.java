package br.eti.gadelha.maps.persistence.payload.response;

import br.eti.gadelha.maps.persistence.model.ChartArea;
import br.eti.gadelha.maps.persistence.model.State;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;
import org.locationtech.jts.geom.*;

@Getter @AllArgsConstructor
public class DTOResponseGaugeStation {

    private UUID id;
    private String number;
    private String title;
    private Point point;

    private State state;
    private ChartArea chartArea;
}