package br.eti.gadelha.maps.persistence.model;

import br.eti.gadelha.maps.persistence.GenericAuditEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import jakarta.persistence.*;
import java.io.Serializable;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = false)
public class State extends GenericAuditEntity {

    private String code;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "country", nullable = true)
    private Country country;
}