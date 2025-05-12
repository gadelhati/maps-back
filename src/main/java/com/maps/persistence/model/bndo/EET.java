package com.maps.persistence.model.bndo;

import com.maps.persistence.model.Commission;
import com.maps.persistence.model.GenericAuditEntity;
import com.maps.persistence.model.Media;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class EET extends GenericAuditEntity {

    private int code;
//    private int cod_tipo_estacao;
//    private int cod_comissao;
//    private int cod_plataforma;
//    private int cod_equipamento;
//    private int cod_midia;

    private LocalDateTime start;
    private LocalDateTime finish;
    private String latitudeBottomMost;
    private String latitudeTopMost;
    @Column(columnDefinition = "geography")
    private Point ne;//lat_topmost and long_rightmost
    private String longitudeLeftMost;
    private String longitudeRightMost;
    @Column(columnDefinition = "geography")
    private Point sw;//lat_bottommost and long_leftmost
    private String fb;
    private String platformBathymetric;//=~platform
    private String secrecyCode;
    private String obs;
    private Integer equipmentDepth;
    private Integer localDepth;
//    private int cod_datum;
    private String nameSeismicLine;
    private LocalDateTime startSeismicLineData;
    private LocalDateTime endSeismicLineData;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "stationCategory", nullable = true)
    private StationCategory stationCategory;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "commission", nullable = true)
    private Commission commission;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "platform", nullable = true)
    private Platform platform;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "equipment", nullable = true)
    private Equipment equipment;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "media", nullable = true)
    private Media media;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "datum", nullable = true)
    private Datum datum;
}