package br.eti.gadelha.maps.persistence.model;

import br.eti.gadelha.maps.persistence.GenericAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.locationtech.jts.geom.Point;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class BlindSignal extends GenericAuditEntity {

    private int number;
    private String name;
    private String chart;
    @Column(columnDefinition = "geography")
    private Point point;
    private String description;
    private String observation;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "chartArea", nullable = true)
    private ChartArea chartArea;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "state", nullable = true)
    private State state;
}