package com.maps.persistence.model.bndo;

import com.maps.persistence.model.GenericAuditEntity;
import com.maps.persistence.model.Country;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class Platform extends GenericAuditEntity {

    private Integer code;
    private String telegraphCallSign;
    private String internationalCallSign;
    private String name;
    private String visualCallSign;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "country", nullable = true)
    private Country country;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "platformCategory", nullable = true)
    private PlatformCategory platformCategory;
}