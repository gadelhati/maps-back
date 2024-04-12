package br.eti.gadelha.maps.persistence.model.bndo;

import br.eti.gadelha.maps.persistence.GenericAuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.locationtech.jts.geom.Point;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class Harbor extends GenericAuditEntity {

    private int code;
    private int latitude;
    private int longitude;
    @Column(columnDefinition = "geography")
    private Point latitudeP;
    @Column(columnDefinition = "geography")
    private Point longitudeP;
    private String name;
    private String chartNumber;
    private Integer reference;
}