package com.maps.persistence.model.bndo;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class Media extends GenericAuditEntity {

    private int code;
//    private int codInstituicao;
//    private int codTipoMidia;
    private String identification;
//    private int sequential;
    private LocalDateTime receipt;
    private LocalDateTime shipping;
    private String obs;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "institution", nullable = true)
    private Institution institution;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "mediaCategory", nullable = true)
    private MediaCategory mediaCategory;
}