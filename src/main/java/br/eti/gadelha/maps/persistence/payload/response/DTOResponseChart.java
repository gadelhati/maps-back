package br.eti.gadelha.maps.persistence.payload.response;

import br.eti.gadelha.maps.persistence.model.ChartArea;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Getter @AllArgsConstructor
public class DTOResponseChart {

    private UUID id;
    private String number;
    private String title;
    private Integer scale;
    private Collection<LocalDateTime> edition;
    private Point ne;
    private Point sw;

    private ChartArea chartArea;
}