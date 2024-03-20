package br.eti.gadelha.maps.persistence.payload.response;

import br.eti.gadelha.maps.persistence.model.ChartArea;
import br.eti.gadelha.maps.persistence.model.State;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import org.locationtech.jts.geom.*;

@Getter @AllArgsConstructor
public class DTOResponseInternationalChart {

    private UUID id;
    private String number;
    private String title;
    private Integer scale;
    private Collection<LocalDateTime> edition;
    private String internationalName;

    private Polygon polygon;
    private ChartArea chartArea;
}