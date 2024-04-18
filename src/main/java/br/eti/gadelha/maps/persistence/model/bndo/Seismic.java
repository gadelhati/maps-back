package br.eti.gadelha.maps.persistence.model.bndo;

import br.eti.gadelha.maps.persistence.GenericAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.locationtech.jts.geom.Point;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class Seismic extends GenericAuditEntity {

    private int point;
    private int latitude;
    private int longitude;
    @Column(columnDefinition = "geography")
    private Point latitudeP;
    @Column(columnDefinition = "geography")
    private Point longitudeP;
    private int qualityControlPosition;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "eet", nullable = true)
    private EET eet;
}