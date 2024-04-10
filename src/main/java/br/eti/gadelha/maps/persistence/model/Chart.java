package br.eti.gadelha.maps.persistence.model;

import br.eti.gadelha.maps.persistence.GenericAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.Collection;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class Chart extends GenericAuditEntity {

    private String number;
    private String title;
    private Integer scale;
    private Collection<LocalDateTime> edition;
    @Column(columnDefinition = "geography")
    private Point ne;
    @Column(columnDefinition = "geography")
    private Point sw;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "chartArea")
    private ChartArea chartArea;
}