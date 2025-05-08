package com.maps.persistence.model.bndo;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class StationCategory extends GenericAuditEntity {

    private Integer code;
    private String alias;
    private String name;
}