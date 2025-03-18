package com.maps.persistence.model.bndo;

import com.maps.persistence.model.GenericAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class Commission extends GenericAuditEntity {

    private Integer code;
    private String cruiseName;
    private String cruiseNumber;
    private String name;
    private String description;
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
    private String areaName;
    private String maximumDepthArea;
    private String minimumDepthArea;
    private String maximumCollectionDepth;
    private String minimumCollectionDepth;
    private String totalSizeMedia;
    private String qualificacao_dados;//se cada equipamento validou os dados
    private String hFolder;
    private String obs;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "project", nullable = true)
    private Project project;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "hydrographicSurvey", nullable = true)
    private HydrographicSurvey hydrographicSurvey;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "coordinator", nullable = true)
    private Institution coordinator;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "responsible", nullable = true)
    private Institution responsible;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "harborArrived", nullable = true)
    private Harbor harborArrived;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "harborDeparture", nullable = true)
    private Harbor harborDeparture;
}