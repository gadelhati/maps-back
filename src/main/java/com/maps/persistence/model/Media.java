package com.maps.persistence.model;

import com.maps.persistence.model.bndo.MediaCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Data
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
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