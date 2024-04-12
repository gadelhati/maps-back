package br.eti.gadelha.maps.persistence.model.bndo;

import br.eti.gadelha.maps.persistence.GenericAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.awt.*;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class Bathymetry extends GenericAuditEntity {

    private int cod_estacao_espaco_tempo;
    private int latitude;
    private int longitude;
    @Column(columnDefinition = "geography")
    private Point position;
    private int depth;
    private int codDatum;
    private int positionQualityControl;
    private int depthQualityControl;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "datum", nullable = true)
    private Datum datum;
//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
//    @JoinColumn(name = "station", nullable = true)
//    private Station station;
}