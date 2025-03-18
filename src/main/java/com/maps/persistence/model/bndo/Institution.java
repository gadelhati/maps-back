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
public class Institution extends GenericAuditEntity {

    private int code;
//    private int codePais;
    private String name;
    private boolean mb;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "country", nullable = true)
    private Country country;
}
