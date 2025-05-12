package com.maps.persistence.model.bndo;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.locationtech.jts.geom.Point;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class Seismic extends GenericAuditEntity {

    private Integer point;
    private Integer latitude;
    private Integer longitude;
    @Column(columnDefinition = "geography")
    private Point latitudeP;
    @Column(columnDefinition = "geography")
    private Point longitudeP;
    private String qualityControl;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "eet", nullable = true)
    private EET eet;
}