package com.maps.persistence.model.bndo;

import com.maps.persistence.model.GenericAuditEntity;
import com.maps.persistence.model.remodel.GeoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.locationtech.jts.geom.Point;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class Seismic extends GeoEntity {

    private String qualityControl;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "eet", nullable = true)
    private EET eet;
}