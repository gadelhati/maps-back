package com.maps.persistence.model.bndo;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalTime;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class UpperAir extends GenericAuditEntity {

//    private Integer cod_estacao;
    private Integer alt;
    private Integer dbc;
    private Integer pressao;
    private Integer dd;
    private Integer fff;
    private Integer ido;
    private Integer temp;
    private Integer ur;
    private LocalTime hora;
    private Integer ascensao;
    private Integer orvalho;
    private String ctrlqc_pressao;
    private String ctrlqc_vento;
    private String ctrlqc_temp;
    private String ctrlqc_orvalho;
    private String ctrlqc_ur;
    private String ctrlqc_alt;
    private String ctrlqc_dd;
    private String ctrlqc_fff;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "station", nullable = true)
    private Station station;
}