package br.eti.gadelha.maps.persistence.model.bndo;

import br.eti.gadelha.maps.persistence.GenericAuditEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class Harbor extends GenericAuditEntity {

    private int code;
    private int latitude;
    private int longitude;
    private String name;
    private String chartNumber;
    private int reference;
}