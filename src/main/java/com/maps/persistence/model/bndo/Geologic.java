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
public class Geologic extends GenericAuditEntity {

    private Integer code;
//    private Integer cod_estacao;
//    private Integer areaOrigin;
    private Integer dragSpeed;
    private Integer dragDirection;
    private Integer dragDuration;
    private boolean photo;
    private boolean photoDepth;
    private String sideScan;
    private String sampleCategory;//?
    private Integer sampleSize;
    private Integer sampleWeight;
    private Integer sampleColor;
    private Integer sampleTexture;
    private boolean concretions;
    private boolean shell;
    private boolean plant;
    private boolean animal;
    private String pollution;
    private Integer tenseMain;
    private Integer tenseComplementary;
    private Integer tenseSample;
    @Column(columnDefinition="text")
    private String obsPreliminary;
    @Column(columnDefinition="text")
    private String obs;
    private Integer survey;
    private Integer structure;
    private String num_sec_amostra; //?
    private Integer lengthMark;
    private Integer colorBottom;
    private Integer colorTop;
    private Integer numnac; //?
    private Integer qualityControlGeologic;
    private Integer gradeSelection;
    private Integer gradeRounding;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "station", nullable = true)
    private Station station;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "country", nullable = true)
    private Country country;
}