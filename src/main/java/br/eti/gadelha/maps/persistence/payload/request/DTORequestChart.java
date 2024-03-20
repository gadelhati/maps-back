package br.eti.gadelha.maps.persistence.payload.request;

import br.eti.gadelha.maps.exception.annotation.UniqueNameCountry;
import br.eti.gadelha.maps.persistence.model.ChartArea;
import lombok.Getter;
import org.locationtech.jts.geom.Polygon;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Getter @UniqueNameCountry
public class DTORequestChart {

    private UUID id;
    private String number;
    private String title;
    private Integer scale;
    private Collection<LocalDateTime> edition;

    private Polygon polygon;
    private ChartArea chartArea;
}