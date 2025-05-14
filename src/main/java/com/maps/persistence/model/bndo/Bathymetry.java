package com.maps.persistence.model.bndo;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.awt.*;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class Bathymetry extends GenericAuditEntity {

//    private int cod_estacao_espaco_tempo;
    private Integer latitude;
    private Integer longitude;
    @Column(columnDefinition = "geography")
    private Point point;
    private Float depth;
//    private int codDatum;
    private String qualityControlPosition;
    private String qualityControlDepth;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "datum", nullable = true)
    private Datum datum;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "eet", nullable = true)
    private EET eet;
}